package com.keralastones.wikipedia

import android.app.Fragment
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.android.volley.Response
import kotlinx.android.synthetic.main.activity_main.mainContainer
import kotlinx.android.synthetic.main.fragment_search.autoCompleteTextView
import kotlinx.android.synthetic.main.fragment_search.searchButton
import kotlinx.android.synthetic.main.fragment_search.searchResultsRecyclerView
import org.json.JSONObject

class SearchFragment : Fragment(),
                       ResultItemListener {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val root = inflater?.inflate(R.layout.fragment_search, container, false)
        return root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchResultsRecyclerView.layoutManager = LinearLayoutManager(context)
        searchButton.setOnClickListener({
            val queryText = autoCompleteTextView.text.toString()
            makeApiRequest(queryText)
            hideKeyboard(it, context)
            autoCompleteTextView.text.clear()
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun makeApiRequest(queryText: String) {
        queryText.replace(" ", "+")
        val url = "https://en.wikipedia.org//w/api.php?action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1" + "&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description&gpslimit=10" + "&gpssearch=" + queryText
        makeRequest(url, getSearchResponseListener(), context)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getSearchResponseListener(): Response.Listener<String> {
        return Response.Listener { response ->
            var searchResults = constructSearchResults(response)
            searchResultsRecyclerView.adapter = SearchResultAdapter(context, searchResults, this)
        }
    }

    private fun constructSearchResults(response: String?): ArrayList<SearchResult> {
        val responseJson = JSONObject(response)
        val pages = responseJson.optJSONObject("query")?.optJSONArray("pages")
        var searchResults = ArrayList<SearchResult>()
        var length = pages?.length()
        if (null == length) {
            length = 0
        }
        for (i in 0 .. length) {
            val page = pages?.optJSONObject(i)
            val pageId = page?.optString("pageid")
            val title = page?.optString("title")
            val imageUrl = page?.optJSONObject("thumbnail")?.optString("source")
            val description = page?.optJSONObject("terms")?.optJSONArray("description")?.optString(0)
            var searchResult = SearchResult(imageUrl, description, title, pageId)
            searchResults.add(searchResult)
        }
        return searchResults
    }

    private fun hideKeyboard(it: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }

    override fun onItemClicked(result: SearchResult, context: Context) {
        val url = "https://en.wikipedia.org/?curid=" + result.pageId
        val webViewFragment = WikipediaWebview.newInstance("", url)
        navigateToFragment(webViewFragment, R.id.mainContainer, fragmentManager, true)
    }

}