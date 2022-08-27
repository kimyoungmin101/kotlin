import 'package:flutter/material.dart';
import 'package:flutter_getx/src/controller/dropdwon_button_controller.dart';
import 'package:get/get.dart';

class DropdownButtonWidget extends GetView<DropdwonButtonController> {
  const DropdownButtonWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Obx(() => DropdownButton(
          value: controller.currentItem.value.index,
          onChanged: (int? value) {
            controller.changeDropDownMenu(value);
          },
          items: DropDownMenu.values
              .map(
                (menu) => DropdownMenuItem(
                  value: menu.index,
                  child: Text(menu.name),
                ),
              )
              .toList(),
        ));
  }
}
