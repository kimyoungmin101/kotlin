import 'package:flutter/material.dart';

class DefaultPage extends StatelessWidget {
  const DefaultPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.green[300],
        child: Center(child: Text("기본페이지"))
    );
  }
}