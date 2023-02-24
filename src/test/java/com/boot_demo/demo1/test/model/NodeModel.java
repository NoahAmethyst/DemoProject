package com.boot_demo.demo1.test.model;


import lombok.Builder;
import lombok.Data;

import java.util.Comparator;
import java.util.Objects;


@Data
@Builder
public class NodeModel implements Comparable<NodeModel>, Comparator<NodeModel> {

    private int a;

    private int b;

    private String name;

    private int index;

    private long timestamp;

    private int isCover = 0;

    @Override
    public int compare(NodeModel o1, NodeModel o2) {
        if (o1.index == o2.index) {
            return 0;
        }
        return o1.index - o2.index > 0 ? 1 : -1;


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeModel nodeModel = (NodeModel) o;
        return a == nodeModel.a ||
                b == nodeModel.b &&
                        isCover == nodeModel.isCover;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a + b, isCover);
    }

    @Override
    public int compareTo(NodeModel o) {
        long r = o.index - this.index;
        if (r > 0) {
            return 1;
        } else if (r < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
