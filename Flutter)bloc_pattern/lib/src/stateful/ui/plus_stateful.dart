import 'package:flutter/material.dart';

import '../components/count_view_sateless.dart';

class PlusStatefulDisplayWidget extends StatefulWidget {
  const PlusStatefulDisplayWidget({Key? key}) : super(key: key);

  @override
  State<PlusStatefulDisplayWidget> createState() => _PlusStatefulDisplayWidgetState();
}

class _PlusStatefulDisplayWidgetState extends State<PlusStatefulDisplayWidget> {
  int count = 0;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("기본 StateFull"),
      ),
      body: CountViewStateless(count : count),
      floatingActionButton: Row(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          IconButton(
            icon: Icon(Icons.add),
            onPressed: (){
              setState(() {
                count++;
              });
            },
          ),
          IconButton(
            icon: Icon(Icons.remove),
            onPressed: (){
              setState(() {
                count--;
              });
            },
          ),
        ],
      ),
    );
  }
}
