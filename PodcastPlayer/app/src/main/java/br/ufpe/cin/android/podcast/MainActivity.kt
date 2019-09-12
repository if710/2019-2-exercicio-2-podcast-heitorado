package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.AsyncTask
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemFeedList = downloadRssFeed().execute(R.string.action_download).get()

        listRecyclerView.layoutManager = LinearLayoutManager(this)

        listRecyclerView.adapter = ItemFeedAdapter(itemFeedList, this)

        listRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    internal inner class downloadRssFeed : AsyncTask<Int, Int, List<ItemFeed> >() {
        override fun doInBackground(vararg resId: Int?): List<ItemFeed> {
            var ret: List<ItemFeed> = ArrayList<ItemFeed>()

            if(resId != null) {
                var uri = resId[0]
                if(uri != null){
                    var feed = URL(getString(uri)).readText()

                    ret = parseRssFeed(feed)
                }

            }

            for(item in ret){
                Log.d("RET", item.toString())
            }

            return ret
        }

        private fun parseRssFeed(feed: String?): List<ItemFeed>{
            var ret: List<ItemFeed> = ArrayList<ItemFeed>()

            if(feed != null) {
                ret = Parser.parse(feed)
            }

            return ret
        }
    }
}
