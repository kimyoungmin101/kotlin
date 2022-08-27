import 'package:flutter/material.dart';

import '../bloc/count_bloc.dart';
import '../components/count_view.dart';

late CountBloc countBloc;

class BlocDisplayWidget extends StatefulWidget {

  const BlocDisplayWidget({Key? key}) : super(key: key);

  @override
  State<BlocDisplayWidget> createState() => _BlocDisplayWidgetState();
}

class _BlocDisplayWidgetState extends State<BlocDisplayWidget> {

  @override
  void initState(){
    super.initState();
    countBloc = CountBloc();
  }

  void dispose(){
    super.dispose();
    countBloc.dispose();
  }
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Bloc 패턴'),
      ),
      body: CountView(),
      floatingActionButton: Row(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          IconButton(
            icon: Icon(Icons.add),
            onPressed: (){
              setState(() {
                countBloc.add();
              });
            },
          ),
          IconButton(
            icon: Icon(Icons.remove),
            onPressed: (){
              setState(() {
                countBloc.subtract();
              });
            },
          ),
        ],
      ),
    );
  }
}
