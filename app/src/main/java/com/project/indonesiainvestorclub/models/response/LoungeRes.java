package com.project.indonesiainvestorclub.models.response;

import com.project.indonesiainvestorclub.models.Charts;
import com.project.indonesiainvestorclub.models.Tweets;

import java.util.List;

public class LoungeRes {
  private List<Tweets> tweets;

  public List<Tweets> getTweets() {
    return tweets;
  }

  public void setTweets(List<Tweets> tweets) {
    this.tweets = tweets;
  }

  private List<Charts> charts;

  public List<Charts> getCharts() {
    return charts;
  }

  public void setCharts(List<Charts> charts) {
    this.charts = charts;
  }

  //    public LoungeRes(Tweets tweets, Charts charts) {
  //
  //    }

  public LoungeRes(List<Tweets> tweets, List<Charts> charts) {
    this.tweets = tweets;
    this.charts = charts;
  }
}
