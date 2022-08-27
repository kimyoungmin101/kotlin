import 'package:bloc_pattern/src/bloc_pattern/ui/bloc_display_widget.dart';
import 'package:bloc_pattern/src/stateful/ui/plus_stateful.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text(widget.title),
        ),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Center(
              child: FlatButton(
                  color: Colors.grey[400],
                  child: Text("bloc 패턴"),
                onPressed: () {
                  Navigator.push(context, MaterialPageRoute(builder: (_) {
                    return BlocDisplayWidget();
                  }));
                },
              ),
            ),
            Center(
              child: FlatButton(
                color: Colors.grey[400],
                child: Text("statefull 패턴"),
                onPressed: () {
                  Navigator.push(context, MaterialPageRoute(builder: (_) {
                    return PlusStatefulDisplayWidget();
                  }));
                },
              ),
            )
          ],
        )
    );
  }
}