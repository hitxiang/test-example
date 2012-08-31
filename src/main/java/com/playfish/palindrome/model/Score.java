package com.playfish.palindrome.model;

import java.util.Comparator;


public class Score implements Comparable<Score> {
  private String id;
  private long score;
  private String name;
  private TYPE type;


  // TYPE helps debuging
  public enum TYPE {
    TOTAL, HIGH
  }
  public static class IdComparator implements Comparator<Score> {

    @Override
    public int compare(Score arg0, Score arg1) {
      return arg0.id.compareTo(arg1.id);
    }

  }

  public Score(TYPE type, String id, String name, long score) {
    this.type = type;
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
    return "Score [type=" + type + ", id=" + id + ", score=" + score + ", name=" + name + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Score other = (Score) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public int compareTo(Score s) {
    return Long.signum(this.score - s.score);
  }


}
