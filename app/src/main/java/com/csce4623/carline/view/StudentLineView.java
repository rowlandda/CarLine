package com.csce4623.carline.view;

import com.csce4623.carline.model.LineStudent;

public interface StudentLineView {
    void refresh();
    void moveUpOne(LineStudent student);
    void moveDownOne(LineStudent student);
    void changePosition(LineStudent student, int pos);
}
