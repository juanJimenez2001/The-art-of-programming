package aop.solution;

import java.util.List;
import java.util.ArrayList;

public class QuickHull {

    public static class Point2 {
	public double x, y;
    }

    // compute distance from a point to a line given by a segment
    public static double pointLineDistance (Point2 point, Point2 start, Point2 end) {
	// first, we obtain a vector perpendicular to start -- end by rotating
	// it 90º
	Point2 perp = new Point2();
	perp.x = -(end.y - start.y); // for the sake of clarity...
	perp.y = end.x - start.x;

	// then we make it normal
	double norm = Math.sqrt(perp.x * perp.x + perp.y * perp.y);
	Point2 normal = new Point2();
	normal.x = perp.x / norm;
	normal.y = perp.y / norm;
	
	// we project start -- point onto normal
	// using the scalar product
	double distance =
	    (point.x - start.x) * normal.x +
	    (point.y - start.y) * normal.y;
	
	return distance;
    }

    public static ArrayList<Point2> QArc (Point2 start, Point2 end, List<Point2> cloud) {

	// is it trivial?
	// trivial problems: a QHProblem is trivial iff the cloud
    	// above it has one or no points.
	// trivial solutions: if there are no points above the segment,
    	// just output the segment. If there is a single point above,
    	// output the triangle start -- above -- end.
	if (cloud.size() == 0) {
	    ArrayList<Point2> segment = new ArrayList<Point2>();
	    segment.add(start);
	    segment.add(end);
	    return segment;
	} else if (cloud.size() == 1) { 
	    ArrayList<Point2> triangle = new ArrayList<Point2>();
	    triangle.add(start);
	    // check that the point is not aligned with start -- end
	    if (QuickHull.pointLineDistance(cloud.get(0),start,end) > 0)
		triangle.add(cloud.get(0));
	    triangle.add(end);
	    return triangle;
	} else { // general case, at least two points in cloud
	    // let us divide the problem into two smaller problems
	    // problem subdivision
	    // find the point in the cloud farthest from the line where
	    // the segment lies (top)
	    // trace two new segments: start -- top and top -- end
	    // the subproblems will be:
	    //   start -- top and the portion of the cloud above it
	    //   top -- end and the portion of the cloud above it
	    
	    // look for pivotal point
	    double maxDistance = 0;
	    Point2 top = new Point2();
	    for (int i = 0; i < cloud.size(); i++) {
		Point2 currentPoint = cloud.get(i);
		double currentDist = QuickHull.pointLineDistance(currentPoint,start,end);
		if (currentDist > maxDistance) {
		    maxDistance = currentDist;
		    top.x = currentPoint.x;
		    top.y = currentPoint.y;
		}
	    }

	    // create subproblems, avoiding top
	    ArrayList<Point2> cloudLeft  = new ArrayList<Point2>();
	    for (int i = 0; i < cloud.size(); i++) {
		Point2 currentPoint = cloud.get(i);
		double currentDist = QuickHull.pointLineDistance(currentPoint,start,top);
		if ((currentPoint.x != top.x || currentPoint.y != top.y) && // getting paranoid about precision
		    currentDist > 0) {
		    cloudLeft.add(currentPoint);
		}
	    }
	    
	    ArrayList<Point2> cloudRight = new ArrayList<Point2>();
	    for (int i = 0; i < cloud.size(); i++) {
		Point2 currentPoint = cloud.get(i);
		double currentDist = QuickHull.pointLineDistance(currentPoint,top,end);
		if ((currentPoint.x != top.x || currentPoint.y != top.y) && // getting paranoid about precision
		    currentDist > 0) {
		    cloudRight.add(currentPoint);
		}
	    }

	    // solve the subproblems
	    ArrayList<Point2> leftArc  = QArc(start,top,cloudLeft);
	    ArrayList<Point2> rightArc = QArc(top,end,cloudRight);
	    
	    // combine the solutions
	    // concatenate both arcs skipping the 1st point of the 2nd one
	    // to avoid repetition
	    for (int i = 1; i < rightArc.size(); i++) {
		leftArc.add(rightArc.get(i));
	    }
	    return leftArc;
	}
    }

    public static List<Point2> QHull(List<Point2> cloud) {
	// first stage is tricky...
	// we first find two extreme points in the cloud, say the
	// leftmost and rightmost ones:
	// PRE: cloud must have at least two points
	Point2 leftmost  = new Point2();
	Point2 rightmost = new Point2();
	leftmost.x = cloud.get(0).x;
	leftmost.y = cloud.get(0).y;
	rightmost.x = cloud.get(0).x;
	rightmost.y = cloud.get(0).y;
	for (int i = 0; i < cloud.size(); i++) {
	    Point2 currentPoint = cloud.get(i);
	    if (currentPoint.x < leftmost.x) {
		leftmost.x = currentPoint.x;
		leftmost.y = currentPoint.y;
	    } else if (currentPoint.x > rightmost.x) {
		rightmost.x = currentPoint.x;
		rightmost.y = currentPoint.y;
	    }
	}

	// then we split the cloud in two:
	List<Point2> northCloud = new ArrayList();
	List<Point2> southCloud = new ArrayList();
	for (int i = 0; i < cloud.size(); i++) {
	    Point2 currentPoint = cloud.get(i);
	    if ((currentPoint.x != leftmost.x  || currentPoint.y != leftmost.y) &&
		(currentPoint.x != rightmost.x || currentPoint.y != rightmost.y)) {
		if (QuickHull.pointLineDistance(currentPoint,leftmost,rightmost) > 0) {// point above line
		    northCloud.add(currentPoint);
		} else { // point below line
		    southCloud.add(currentPoint);
		}
	    }
	}

	// we call QArc on each half:
	ArrayList<Point2> upper = QArc(leftmost,rightmost,northCloud);
	ArrayList<Point2> lower = QArc(rightmost,leftmost,southCloud);

	// and combine them to produce the complete hull
	for (int i = 1; i < lower.size()-1; i++) {
	    upper.add(lower.get(i));
	}

	return upper;
    }

    // some tests...
    public static void main (String[] idontcare) {
	// // testing distances
	// Point2 p = new Point2();
	// Point2 q = new Point2();
	// Point2 r = new Point2();
	// // p = (1,3)
	// p.x = 1;
	// p.y = 3;
	// // q = (5, 2);
	// q.x = 5;
	// q.y = 2;
	// // r = (10,4)
	// r.x = 10;
	// r.y = 4;

	// System.out.printf("The distance of (%f,%f) to the line defined by (%f,%f) and (%f,%f) is %f./n",
	// 		  r.x, r.y, p.x, p.y, q.x, q.y,
	// 		  QuickHull.pointLineDistance(r,p,q));

	// System.out.printf("The distance of (%f,%f) to the line defined by (%f,%f) and (%f,%f) is %f./n",
	// 		  q.x, q.y, p.x, p.y, r.x, r.y,
	// 		  QuickHull.pointLineDistance(q,p,r));

	// test QHull
	Point2[] points = new Point2[10];
	for (int i = 0; i < 10; i++) {
	    points[i] = new Point2();
	}
	points[0].x = 1; points[0].y = 5;
	points[1].x = 2; points[1].y = 2;
	points[2].x = 3; points[2].y = 4;
	points[3].x = 4; points[3].y = 3;
	points[4].x = 4; points[4].y = 6;
	points[5].x = 4; points[5].y = 7;
	points[6].x = 5; points[6].y = 5;
	points[7].x = 6; points[7].y = 3;
	points[8].x = 7; points[8].y = 4;
	points[9].x = 7; points[9].y = 6;

	ArrayList<Point2> cloud = new ArrayList();
	
	for (int i = 0; i < 10; i++) {
	    cloud.add(points[i]);
	}

	List<Point2> hull = QuickHull.QHull(cloud);

	for (int i = 0; i < hull.size(); i++) {
	    System.out.printf("(%f,%f) -- ", hull.get(i).x, hull.get(i).y);
	}
    }
} // end class QuickHull

