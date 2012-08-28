package com.playfish.palindrome.model;


public class Score implements Comparable<Score> {
  private String id;
  private long score;
  private String name;


  public Score(String id, String name, long score) {
    this.id = id;
    this.name = name;
    this.score = score;
  }


  public String getName() {
    return name;
  }

  public long getScore() {
    return score;
  }

  public void setScore(long score) {
    this.score = score;
  }

  @Override
  public String toString() {
    return "Score [id=" + id + ", score=" + score + ", name=" + name + "]";
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    Score other = (Score) obj;
    return id.equals(other.id);
  }

  @Override
  public int compareTo(Score s) {
    return Long.signum(this.score - s.score);
  }


}
