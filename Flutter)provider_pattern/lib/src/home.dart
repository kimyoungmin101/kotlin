import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:provider_pattern/src/provider/count_provider.dart';
import 'package:provider_pattern/src/ui/count_home.dart';

class Home extends StatelessWidget {
  late CountProvider _countProvider;

  @override
  Widget build(BuildContext context) {

    _countProvider = Provider.of<CountProvider>(context, listen: false);

    return Scaffold(
      appBar: AppBar(
        title: Text("Provder Pattern"),
      ),
      body: CountHomeWidget(),
      floatingActionButton: Row(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          IconButton(onPressed: () {
            _countProvider.add();
          }, icon: Icon(Icons.add)),
          IconButton(onPressed: () {
            _countProvider.remove();
          }, icon: Icon(Icons.remove)),
        ],
      ),
    );
  }
}
