package jp.ac.oit.igakilab.dwr.multiple;

public class TeamNowForm {
	private String name;
	private int level;
	private String member1;
	private String member2;
	private String member3;
	private int exp;
	private String[] task;
	private int goal;
	private int nowtime;


	public int getNowtime() {
		return nowtime;
	}
	public void setNowtime(int nowtime) {
		this.nowtime = nowtime;
	}
	public int getGoal() {
		return goal;
	}
	public void setGoal(int goal) {
		this.goal = goal;
	}
	public String[] getTask() {
		return task;
	}
	public void setTask(String[] task) {
		this.task = task;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getMember1() {
		return member1;
	}
	public void setMember1(String member1) {
		this.member1 = member1;
	}
	public String getMember2() {
		return member2;
	}
	public void setMember2(String member2) {
		this.member2 = member2;
	}
	public String getMember3() {
		return member3;
	}
	public void setMember3(String member3) {
		this.member3 = member3;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
}
