package doMain;

public class Student {
    private int id;
    private String name;
    private String sex;
    private int math;
    private int chinese;
    private int englishi;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", math=" + math +
                ", chinese=" + chinese +
                ", englishi=" + englishi +
                '}';
    }

    public int getEnglishi() {
        return englishi;
    }

    public void setEnglishi(int englishi) {
        this.englishi = englishi;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getChinese() {
        return chinese;
    }

    public void setChinese(int chinese) {
        this.chinese = chinese;
    }





    public Student(int id, String name, String sex, int math, int chinese, int english) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.math = math;
        this.chinese = chinese;
        this.englishi = english;
    }

    public Student() {

    }
}
