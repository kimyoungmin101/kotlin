import 'package:flutter/material.dart';
import 'package:provider_pattern/src/provider/count_provider.dart';
import 'package:provider/provider.dart';

class CountHomeWidget extends StatelessWidget {
  late CountProvider _countProvider;

  @override
  Widget build(BuildContext context) {
    _countProvider = Provider.of<CountProvider>(context);
    // Consumer를 사용해서 이부분만 업데이트 한다.
    return Center(
        child: Consumer<CountProvider>(
            builder: (context, provider, child) {
              return Text(
                provider.count.toString(),
                style: TextStyle(fontSize: 80),
              );
            }
        )
    );
  }
}
