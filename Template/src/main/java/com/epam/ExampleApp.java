
package com.epam;

import com.epam.api.GpsNavigator;
import com.epam.api.Path;
import com.epam.impl.MyException;
import com.epam.impl.Node;
import com.epam.impl.Way;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.MAX_VALUE;

/**
 * This class app demonstrates how your implementation of {@link com.epam.api.GpsNavigator} is intended to be used.
 */
public class ExampleApp {


    private static ArrayList<Way> ways;
    private static HashSet<Node> nodes;
    private static Node node;


    public static void main(String[] args) {
        ways = new ArrayList();
        nodes = new HashSet<>();
        node = new Node();

        final GpsNavigator navigator = new StubGpsNavigator();
        navigator.readData("./Template/src/main/java/com/epam/impl/ways.txt");

        final Path path = navigator.findPath("N", "B");
        System.out.println(path);
    }

    private static class StubGpsNavigator implements GpsNavigator {

        private Node findNodeByName(String name) {
            Node nodeByName = new Node();
            if (!nodes.isEmpty()) {
                for (Node nodeTemp : nodes) {
                    if (nodeTemp.getName().equals(name)) {
                        nodeByName = nodeTemp;
                    }
                }
            }
            if (nodeByName.getName() == null) {
                try {
                    throw new MyException();
                } catch (MyException e) {
                    System.out.println("There is NO node with that name!");
                    System.exit(0);
                }
            }
            return nodeByName;
        }

        private ArrayList<String> reverseArray(ArrayList<String> arrayList) {
            ArrayList<String> tempArrayList = new ArrayList<>();
            if (!arrayList.isEmpty()) {
                for (int i = arrayList.size() - 1; i >= 0; i--) {
                    tempArrayList.add(arrayList.get(i));
                }
            }
            return tempArrayList;
        }

        @Override
        public void readData(String filePath) {
            try {
                Scanner in = new Scanner(new File(filePath));

                String s;
                String[] items;
                String delimeter = " ";
                Way way;
                Node node;
                Set<String> nameNodes = new HashSet<>();
                while (in.hasNextLine()) {
                    way = new Way();

                    s = in.nextLine();
                    items = s.split(delimeter);

                    if (items.length == 4) { //check truth of formatting
                        way.setPointOfDeparture(items[0]);
                        way.setDestinationPoint(items[1]);
                        try {
                            way.setLength(Integer.valueOf(items[2]));
                            way.setCostOfTheWay(Integer.valueOf(items[3]));
                        } catch (NumberFormatException e) {
                            System.out.println("Incorrect graph description file format!");
                            System.exit(0);
                        }
                        ways.add(way);

                        nameNodes.add(items[0]);
                        nameNodes.add(items[1]);
                    } else {
                        try {
                            throw new MyException();
                        } catch (MyException e) {
                            System.out.println("Incorrect graph description file format!");
                            System.exit(0);
                        }
                    }

                }
                in.close();

                for (String singleNode : nameNodes) {
                    node = new Node();
                    node.setName(singleNode);
                    node.setWeight(MAX_VALUE);
                    node.setCostFromPointOfDeparture(0);
                    ArrayList<String> destinationPoints = new ArrayList<>();
                    HashSet<String> pointsOfDepartures = new HashSet<>();
                    for (Way tempWay : ways) {
                        if (tempWay.getPointOfDeparture().equals(node.getName())) {
                            destinationPoints.add(tempWay.getDestinationPoint());
                        }
                        if (tempWay.getDestinationPoint().equals(node.getName())) {
                            pointsOfDepartures.add(tempWay.getPointOfDeparture());
                        }
                    }
                    node.setDestinationPoints(destinationPoints);
                    node.setPointsOfDepartures(pointsOfDepartures);
                    nodes.add(node);
                }

//                System.out.println(ways.toString());
//                System.out.println(nameNodes.toString());
//                System.out.println(nodes.toString());
//                System.out.println("****--Ways and Nodes have built--****");

            } catch (IOException e) {
                System.out.println("errorSKTL" + e);
            }
        }


        @Override
        public Path findPath(String pointA, String pointB) {
            ArrayList<String> pathByNodes = new ArrayList<>();
            HashSet<String> desPoints;
            HashSet<String> pointsOfDep;
            int cost = 0;
            Node nodeEndOfTheRib;
            Node nodeStartOfTheRib;
            int sizeOfUncheckedNodes = 0;

            if (nodes.size() > 2) {
                sizeOfUncheckedNodes = nodes.size();
            } else {
                try {
                    throw new MyException();
                } catch (MyException e) {
                    System.out.println("The graph has less than two nodes!");
                    System.exit(0);
                }
            }

            //finding the first node of the path and setWeight(0)
            findNodeByName(pointA).setWeight(0);

            while (sizeOfUncheckedNodes > 0) {
                for (Node node : nodes) {
                    desPoints = new HashSet<>();
                    pointsOfDep = new HashSet<>();
                    if ((node.getWeight() < MAX_VALUE)) {

                        //finding nodes by pointOfDeparture
                        for (Way way : ways) {
                            if (node.getName().equals(way.getPointOfDeparture())) {
                                if (node.getPointOfDeparture() != null) {
                                    pointsOfDep.add(node.getPointOfDeparture());
                                }


                                //finding end of the rib
                                nodeEndOfTheRib = findNodeByName(way.getDestinationPoint());
                                if (way.getLength() + node.getWeight() < nodeEndOfTheRib.getWeight()) {

                                    nodeEndOfTheRib.setWeight(way.getLength() + node.getWeight());
                                    nodeEndOfTheRib.setCostFromPointOfDeparture(node.getCostFromPointOfDeparture()
                                            + way.getCostOfTheWay());
                                    nodeEndOfTheRib.setPointOfDeparture(way.getPointOfDeparture());
                                }
                            }
                        }
                        //finding nodes by DestinationPoint
                        for (Way way : ways) {
                            if (node.getName().equals(way.getDestinationPoint())) {

                                if (node.getWeight() < MAX_VALUE) {
                                    desPoints.add(way.getDestinationPoint());
                                }

                                //finding start of the rib
                                nodeStartOfTheRib = findNodeByName(way.getPointOfDeparture());
                                if (way.getLength() + nodeStartOfTheRib.getWeight() < node.getWeight()
                                        && nodeStartOfTheRib.getWeight() < MAX_VALUE) {

                                    node.setWeight(way.getLength() + nodeStartOfTheRib.getWeight());
                                    node.setCostFromPointOfDeparture(nodeStartOfTheRib.getCostFromPointOfDeparture()
                                            + way.getCostOfTheWay());
                                    node.setPointOfDeparture(way.getPointOfDeparture());
                                }
                            }
                        }
                    }
                }
                sizeOfUncheckedNodes--;
            }
//                System.out.println(nodes);


            //building the path
            Node tNode;
            tNode = findNodeByName(pointB);
            cost = tNode.getCostFromPointOfDeparture();
            Node tempNode = findNodeByName(pointB);
            int mark = 0;
            boolean bool = false;


            while (!((tNode.getCostFromPointOfDeparture() == 0) && tNode.getWeight() == 0)) {

                if (tNode.getPointOfDeparture() == null) {
                    try {
                        throw new MyException();
                    } catch (MyException e) {
                        System.out.println("There is NO such path!");
                        System.exit(0);
                    }
                }

                Node checkNode;

                for (String n : tempNode.getPointsOfDepartures()) {

                    checkNode = findNodeByName(n);

                    for (Way way : ways) {
                        if (way.getPointOfDeparture().equals(n)
                                && way.getDestinationPoint().equals(pointB)
                                && !checkNode.isChecked()) {
                            if (tempNode.getWeight() - way.getCostOfTheWay() == checkNode.getWeight()
                                    && !checkNode.isChecked()) {
                                mark++;

                                if (mark > 1) {
                                    try {
                                        throw new MyException();
                                    } catch (MyException e) {
                                        System.out.println("There is more then 1 shortest path!");
                                        System.exit(0);
                                    }

                                }

                                checkNode.setChecked(true);
                            }
                        }
                    }
                }

                pathByNodes.add(tNode.getName());

                tNode = findNodeByName(tNode.getPointOfDeparture());
            }
            pathByNodes.add(tNode.getName());

            return new Path(reverseArray(pathByNodes), cost);
        }
    }
}

