import 'package:flutter/material.dart';
import 'package:flutter_getx/src/app.dart';
import 'package:flutter_getx/src/controller/dropdwon_button_controller.dart';
import 'package:get/get.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      initialBinding: BindingsBuilder((){
        Get.put(DropdwonButtonController());
      }),
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: App()
    );
  }
}
