import 'dart:ffi';

import 'package:get/get.dart';

enum DropDownMenu { DEFAULT, MENU1, MENU2}

extension DropDownMenuExtention on DropDownMenu{
  String get name{
    switch(this){
      case DropDownMenu.DEFAULT:
        return "기본메뉴";
      case DropDownMenu.MENU1:
        return "Menu 1";
      case DropDownMenu.MENU2:
        return "Menu 2";
    }
  }
}

class DropdwonButtonController extends GetxController {
  Rx<DropDownMenu> currentItem = DropDownMenu.DEFAULT.obs;

  void changeDropDownMenu(int? itemIndex){
    var selectedItem = DropDownMenu.values.where((menu) => menu.index == itemIndex).first; // 매칭이 되는 값을 찾아 낼 수 있다.
    currentItem(selectedItem);
  }
}

