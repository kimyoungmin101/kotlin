import 'package:flutter/cupertino.dart';

// 상태관리를 하는 변수
class CountProvider extends ChangeNotifier{
  int _count = 0;
  int get count => _count; // 외부에서 접근 가능

  add(){
    _count += 1;
    notifyListeners();
  }

  remove(){
    _count -= 1;
    notifyListeners(); // 상태값 업데이트
  }
}