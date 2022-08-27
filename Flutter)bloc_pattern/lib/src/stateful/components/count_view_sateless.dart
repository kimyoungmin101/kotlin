import 'package:flutter/material.dart';


class CountViewStateless extends StatelessWidget {
  int count;
  CountViewStateless({Key? key, required this.count}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Text(
        count.toString(),
        style: TextStyle(fontSize: 80),
      ),
    );
  }
}
