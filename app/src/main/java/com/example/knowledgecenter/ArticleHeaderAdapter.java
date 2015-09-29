package com.example.knowledgecenter;
import java.util.List;

import com.example.knowledgecenter.ArticleHeader;
import com.example.knowledgecenter.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ArticleHeaderAdapter extends BaseAdapter {
	private Activity activity;
    private LayoutInflater inflater;
    private List<ArticleHeader> articleHeaders;
	
	public ArticleHeaderAdapter (Activity activity, List<ArticleHeader> listArticles)
	{
		articleHeaders = listArticles;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return articleHeaders.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return articleHeaders.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);
 
        //if (imageLoader == null)
         //   imageLoader = AppController.getInstance().getImageLoader();
        //NetworkImageView thumbNail = (NetworkImageView) convertView
          //      .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        //TextView rating = (TextView) convertView.findViewById(R.id.rating);
        //TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);
 
        // getting movie data for the row
        ArticleHeader m = articleHeaders.get(position);
 
        // thumbnail image
        //thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
         
        // title
        title.setText(m.getTitle());
         
        // rating
        /**String prefs = m.getPrefs();
        prefs.replace("[\"", "");
        prefs.replace("\"]", "");
        rating.setText(String.valueOf(prefs));**/
         
        // genre
        /**String genreStr = "";
        for (String str : m.getGenre()) {
            genreStr += str + ", ";
        }
        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;
        genre.setText(genreStr);**/
         
        // release year
        year.setText(String.valueOf(m.getTime()));
 
        return convertView;
	}

}
