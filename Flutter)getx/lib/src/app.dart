import 'package:flutter/material.dart';
import 'package:flutter_getx/src/pages/default_page.dart';
import 'package:flutter_getx/src/pages/page1.dart';
import 'package:flutter_getx/src/pages/page2.dart';
import 'components/dropdown_button.dart';
import 'package:get/get.dart';

import 'controller/dropdwon_button_controller.dart';

class App extends GetView<DropdwonButtonController> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("DropDwon Sample"),
      ),
      body: Column(
        children: [
          DropdownButtonWidget(),
          Expanded(child: Obx((){
            switch(controller.currentItem.value){
              case DropDownMenu.DEFAULT:
                return DefaultPage();
              case DropDownMenu.MENU1:
                return Page1();
              case DropDownMenu.MENU2:
                return Page2();
            }
          }
          ))
        ],
      ),
    );
  }
}

