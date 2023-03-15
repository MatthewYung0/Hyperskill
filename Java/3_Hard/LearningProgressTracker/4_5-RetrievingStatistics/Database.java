package tracker;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Database {

    enum COURSES {
        JAVA(0),
        DSA(1),
        DATABASES(2),
        SPRING(3);
        final int course;

        COURSES(int course) {
            this.course = course;
        }

        private static final String[] courses;

        static {
            courses = enumsToStringArray();
        }

        public static String[] enumsToStringArray() {
            String[] courses = new String[COURSES.values().length];
            int index = 0;
            for (COURSES course : COURSES.values()) {
                courses[index] = course.toString();
                index++;
            }
            return courses;
        }
    }

    private String mostPopular = "n/a";
    private String leastPopular = "n/a";
    private String highestActivity = "n/a";
    private String lowestActivity = "n/a";
    private String hardestCourse = "n/a";
    private String easiestCourse = "n/a";

    private final HashMap<Integer, Student> database = new HashMap<>();
    private final int[] activityTracker = new int[COURSES.values().length];
    private final int[] enrolledStudents = new int[COURSES.values().length];
    private final double[] averagePoints = new double[COURSES.values().length];

    public HashMap<Integer, Student> getDatabase() {
        return this.database;
    }

    public LinkedHashMap<Integer, Integer> getTopStudents(COURSES course) {
        LinkedHashMap<Integer, Integer> topStudents = new LinkedHashMap<>();
        for (Map.Entry<Integer, Student> entry : database.entrySet()) {
            switch (course) {
                case JAVA:
                    if (entry.getValue().getJavaPoints() > 0) {
                        topStudents.put(entry.getKey(), entry.getValue().getJavaPoints());
                    }
                    break;
                case DSA:
                    if (entry.getValue().getDSAPoints() > 0) {
                        topStudents.put(entry.getKey(), entry.getValue().getDSAPoints());
                    }
                    break;
                case DATABASES:
                    if (entry.getValue().getDatabasePoints() > 0) {
                        topStudents.put(entry.getKey(), entry.getValue().getDatabasePoints());
                    }
                    break;
                default:
                    if (entry.getValue().getSpringPoints() > 0) {
                        topStudents.put(entry.getKey(), entry.getValue().getSpringPoints());
                    }
                    break;
            }
        }
        return sortMap(topStudents);
    }

    public LinkedHashMap<Integer, Integer> sortMap(LinkedHashMap<Integer, Integer> map) {
        Comparator<Map.Entry<Integer, Integer>> comparator = (o2, o1) -> {
            int i = o1.getValue().compareTo(o2.getValue());
            if (i == 0) {
                return o2.getKey().compareTo(o1.getKey());
            } else {
                return i;
            }
        };
        return map.
                entrySet().
                stream().
                sorted(comparator).
                collect((Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)));
    }


    public void updateCourseData() {
        if (database.size() != 0) {
            setMostPopular();
            setLeastPopular();
            setHighestActivity();
            setLowestActivity();
            setHardestCourse();
            setEasiestCourse();
        }
    }

    public void setMostPopular() {
        this.mostPopular = getHigh(this.enrolledStudents);
    }

    public void setLeastPopular() {
        String [] temp = this.mostPopular.split(", ");
        if (temp.length == 4) {
            this.leastPopular = "n/a";
        } else {
            this.leastPopular = getLow(this.enrolledStudents);
        }
    }

    public void setHighestActivity() {
        this.highestActivity = getHigh(this.activityTracker);
    }

    public void setLowestActivity() {
        String [] temp = this.highestActivity.split(", ");
        if (temp.length == 4) {
            this.lowestActivity = "n/a";
        } else {
            this.lowestActivity = getLow(activityTracker);
        }
    }

    public void setEasiestCourse() {
        StringBuilder easiestCourse = new StringBuilder();
        double lowestNumber = 0;
        for (int courseNo = 0; courseNo < averagePoints.length; courseNo++) {
            String course = COURSES.courses[courseNo];
            course = course.substring(0, 1).toUpperCase() + course.substring(1).toLowerCase();
            if (averagePoints[courseNo] > lowestNumber) {
                easiestCourse.setLength(0);
                easiestCourse.append(course).append(", ");
                lowestNumber = averagePoints[courseNo];
            } else if (averagePoints[courseNo] == lowestNumber) {
                easiestCourse.append(course).append(", ");
            }
        }
        this.easiestCourse = easiestCourse.deleteCharAt(easiestCourse.length() - 2).toString().trim();
    }

    public void setHardestCourse() {
        StringBuilder hardestCourses = new StringBuilder();
        double lowestNumber = Double.MAX_VALUE;
        for (int courseNo = 0; courseNo < averagePoints.length; courseNo++) {
            String course = COURSES.courses[courseNo];
            course = course.substring(0, 1).toUpperCase() + course.substring(1).toLowerCase();
            if (averagePoints[courseNo] < lowestNumber) {
                hardestCourses.setLength(0);
                hardestCourses.append(course).append(", ");
                lowestNumber = averagePoints[courseNo];
            } else if (averagePoints[courseNo] == lowestNumber) {
                hardestCourses.append(course).append(", ");
            }
        }
        this.hardestCourse = hardestCourses.deleteCharAt(hardestCourses.length() - 2).toString().trim();
    }

    public void updateEnrollment(int ID, int[] points) {
        Student student = getDatabase().get(ID);
        if (student.getJavaPoints() == 0 && points[0] > 0) {
            this.enrolledStudents[0]++;
        }
        if (student.getDSAPoints() == 0 && points[1] > 0) {
            this.enrolledStudents[1]++;
        }
        if (student.getDatabasePoints() == 0 && points[2] > 0) {
            this.enrolledStudents[2]++;
        }
        if (student.getSpringPoints() == 0 && points[3] > 0) {
            this.enrolledStudents[3]++;
        }
    }

    public void updateActivity(int[] points) {
        if (points[0] > 0) {
            activityTracker[0] += 1;
        }
        if (points[1] > 0) {
            activityTracker[1] += 1;
        }
        if (points[2] > 0) {
            activityTracker[2] += 1;
        }
        if (points[3] > 0) {
            activityTracker[3] += 1;
        }
    }

    public void updateAveragePoints() {
        double[] totalPoints = getTotalPoints();
        for (int course = 0; course < averagePoints.length; course++) {
            averagePoints[course] = (totalPoints[course] / activityTracker[course]);
        }
    }

    public double[] getTotalPoints() {
        double[] totalPoints = new double[COURSES.values().length];
        for (Map.Entry<Integer, Student> set : database.entrySet()) {
            totalPoints[0] += set.getValue().getJavaPoints();
            totalPoints[1] += set.getValue().getDSAPoints();
            totalPoints[2] += set.getValue().getDatabasePoints();
            totalPoints[3] += set.getValue().getSpringPoints();
        }
        return totalPoints;
    }

    public String getLow(int[] array) {
        StringBuilder low = new StringBuilder();
        int lowestNumber = Integer.MAX_VALUE;
        for (int courseNo = 0; courseNo < array.length; courseNo++) {
            String course = COURSES.courses[courseNo];
            course = course.substring(0, 1).toUpperCase() + course.substring(1).toLowerCase();
            if (array[courseNo] < lowestNumber) {
                low.setLength(0);
                low.append(course).append(", ");
                lowestNumber = array[courseNo];
            } else if (array[courseNo] == lowestNumber) {
                low.append(course).append(", ");
            }
        }
        return low.deleteCharAt(low.length() - 2).toString().trim();
    }

    public String getHigh(int[] array) {
        StringBuilder high = new StringBuilder();
        int highestNumber = 0;
        for (int courseNo = 0; courseNo < array.length; courseNo++) {
            String course = COURSES.courses[courseNo];
            course = course.substring(0, 1).toUpperCase() + course.substring(1).toLowerCase();
            if (array[courseNo] > highestNumber) {
                high.setLength(0);
                high.append(course).append(", ");
                highestNumber = array[courseNo];
            } else if (array[courseNo] == highestNumber) {
                high.append(course).append(", ");
            }
        }
        return high.deleteCharAt(high.length() - 2).toString().trim();
    }

    public void printStatistics() {
        updateCourseData();
        System.out.println("Most popular: " + this.mostPopular);
        System.out.println("Least popular: " + this.leastPopular);
        System.out.println("Highest activity: " + this.highestActivity);
        System.out.println("Lowest activity: " + this.lowestActivity);
        System.out.println("Easiest course: " + this.easiestCourse);
        System.out.println("Hardest course: " + this.hardestCourse);
    }
}
