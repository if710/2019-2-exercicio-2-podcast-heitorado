package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.AsyncTask
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.net.URL
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listRecyclerView.layoutManager = LinearLayoutManager(this)

        //listRecyclerView.adapter = ItemFeedAdapter(itemFeedList, this)

        listRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        downloadRssFeed().execute(R.string.action_download)
    }

    internal inner class downloadRssFeed : AsyncTask<Int, Int, List<ItemFeed> >() {
        override fun doInBackground(vararg resId: Int?): List<ItemFeed> {
            var parsedFeed: List<ItemFeed> = ArrayList<ItemFeed>()

            if(resId != null) {
                var uri = resId[0]
                if(uri != null){

                    var feed  = try {
                        URL(getString(uri)).readText()
                    } catch(e : Exception) {
                        null
                    }

                    parsedFeed = parseRssFeed(feed)

                }

            }

            val db = ItemFeedDB.getDatabase(applicationContext)

            for(item in parsedFeed){
                //Log.d("RET", item.toString())
                db.itemFeedDAO().insertItems(item)
            }

            // Retorna SELECT * do db ao invés do parsedFeed.
            return db.itemFeedDAO().allItems()
        }

        override fun onPostExecute(result: List<ItemFeed>?) {
            if(result != null) {
                listRecyclerView.adapter = ItemFeedAdapter(result, applicationContext)
            } else {
                listRecyclerView.adapter = ItemFeedAdapter(ArrayList<ItemFeed>(), applicationContext)
            }
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
