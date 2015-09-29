package com.example.knowledgecenter;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.os.Build;

public class ArticleActivity extends ActionBarActivity {
	public static String title;
	public static String body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		Intent intent = getIntent();
		 title = intent.getStringExtra("Header");
		 body = intent.getStringExtra("Body");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.article, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		/**int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}**/
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		WebView article;
		 ProgressDialog pd;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_article,
					container, false);
			article = (WebView)rootView.findViewById(R.id.webViewArticle);
			String html = "<html><body>Hello, World!</body></html>";
			String mime = "text/html";
			String encoding = "utf-8";

			//WebView myWebView = (WebView)this.findViewById(R.id.myWebView);
			article.getSettings().setJavaScriptEnabled(true);
			article.loadData( Html.fromHtml(body).toString(), "text/html", "UTF-8");
			pd = ProgressDialog.show(getActivity(), "", "Please wait, your transaction is being processed...", true);
   
			article.getSettings().setBuiltInZoomControls(true);
			article.getSettings().setTextZoom(50);
			article.setWebViewClient(new WebViewClient() {
				
				 public void onPageFinished(WebView view, String url) {
		                if (pd.isShowing()) {
		                    pd.dismiss();
		                }
				 }


		        @Override
		        public boolean shouldOverrideUrlLoading(WebView view, String url) {
		           // Here put your code
		              Log.d("My Webview", url);
		              if(url.endsWith(".pdf"))
		              {
		            	  article.getSettings().setBuiltInZoomControls(false);
		            	  article.loadUrl("https://docs.google.com/viewer?url="+url+"&embedded=true");
		              
		              }else{
		            	  article.getSettings().setBuiltInZoomControls(true);
		            	  article.loadUrl(url);
		              }
		              
		              
		              pd = ProgressDialog.show(getActivity(), "", "Please wait, your transaction is being processed...", true);

		           // return true; //Indicates WebView to NOT load the url;
		              return false; //Allow WebView to load url
		        }
		    });
			
			article.setOnKeyListener(new OnKeyListener()
			{
			    @Override
			    public boolean onKey(View v, int keyCode, KeyEvent event)
			    {
			        if(event.getAction() == KeyEvent.ACTION_DOWN)
			        {
			            WebView webView = (WebView) v;

			            switch(keyCode)
			            {
			                case KeyEvent.KEYCODE_BACK:
			                    if(webView.canGoBack())
			                    {
			                        webView.goBack();
			                        return true;
			                    }
			                    break;
			            }
			        }

			        return false;
			    }
			});
					//TextView txtTitle = (TextView)rootView.findViewById(R.id.txtHeader);
			//TextView txtMain = (TextView)rootView.findViewById(R.id.txtMain);
			//txtTitle.setText(title.toString());
			//if(body.contains("<p>"))
			//{
				//txtMain.setText((Html.fromHtml("<body>"+body.toString()+"</body>")));
			//}else
			//{
				//txtMain.setText((body.toString()));
			//}
				
			//getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			android.app.ActionBar actionbar = getActivity().getActionBar();
			 actionbar.setTitle(title);
			 actionbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sbBlue)));
			 actionbar.setDisplayHomeAsUpEnabled(true);
			return rootView;
		}
		
	
		
		
	}
}
