import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:provider_pattern/src/home.dart';
import 'package:provider_pattern/src/provider/count_provider.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: ChangeNotifierProvider(  // ChangeNotifierProvider를 통해 변화에 대해 구독(하나만 구독 가능)
          create: (BuildContext context) => CountProvider(), // count_provider.dart
          child: Home() // home.dart // child 하위에 모든 것들은 CountProvider에 접근 할 수 있다.
      )
    );
  }
}