package com.example.aop_part3_chaptor06.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aop_part3_chaptor06.DBKey.Companion.CHILD_CHAT
import com.example.aop_part3_chaptor06.DBKey.Companion.DB_ARTICLES
import com.example.aop_part3_chaptor06.DBKey.Companion.DB_USERS
import com.example.aop_part3_chaptor06.R
import com.example.aop_part3_chaptor06.chatList.ChatListItem
import com.example.aop_part3_chaptor06.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeListFragment : Fragment(R.layout.fragment_home) {
    private var binding: FragmentHomeBinding? = null

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleDB: DatabaseReference
    private lateinit var userDB: DatabaseReference

    private val articleList = mutableListOf<ArticleModel>()

    private val listener = object: ChildEventListener {

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            // DB에 새로운 객체가 추가 되면 작동하는 Listener이다.
            val articleModel = snapshot.getValue(ArticleModel::class.java)
            articleModel ?: return

            articleList.add(articleModel)
            articleAdapter.submitList(articleList)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onChildRemoved(snapshot: DataSnapshot) {}
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}
    }

    private val auth: FirebaseAuth by lazy { Firebase.auth }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding
        // 절대 null이 될 수 없는 binding이 된다 !
        articleList.clear()

        articleAdapter = ArticleAdapter(
            onItemClicked = { articleModel ->
                if (auth.currentUser != null) {
                    // 로그인을 한상태
                    if (auth.currentUser?.uid != articleModel.sellerId) {
                        // 내가 올리지 않은 아이템
                        val chatRoom = ChatListItem(
                            buyerId = auth.currentUser!!.uid,
                            sellerId = articleModel.sellerId,
                            itemTitle = articleModel.title,
                            key = System.currentTimeMillis()
                        )
                        // 내가 올리지 않은 아이템이면 chatRoom을 만들어준다.
                        userDB.child(auth.currentUser!!.uid)
                            .child(CHILD_CHAT) // 'chat' 라는 이름의 CHILD_CHAT을 child로 만들어줌
                            .push()
                            .setValue(chatRoom)

                        userDB.child(articleModel.sellerId)
                            .child(CHILD_CHAT)
                            .push()
                            .setValue(chatRoom)

                        Snackbar.make(view, "채팅방이 생성됐습니다. 채팅앱에서 확인해주세요,. ", Snackbar.LENGTH_SHORT)
                            .show()
                    } else {
                        // 내가 올린 아이템
                        Snackbar.make(view, "내가 올린 물품 입니다. ", Snackbar.LENGTH_SHORT).show()
                    }
                } else {
                    Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_SHORT).show()
                }
            })

        articleDB =
            Firebase.database.reference.child(DB_ARTICLES) // Articles라는 database.reference의 최상위에 바로 아래있는 userDB를 Articles를 가져온것,
        userDB =
            Firebase.database.reference.child(DB_USERS) // Users라는 database.reference의 최상위에 바로 아래있는 users를 가져온것
        // 위의 DB들은 처음에는 빈상태로 시작한다.

        fragmentHomeBinding.itemRecyclerView.layoutManager = LinearLayoutManager(context)
        fragmentHomeBinding.itemRecyclerView.adapter = articleAdapter

        fragmentHomeBinding.addFloatingButton.setOnClickListener {
            context?.let {
                if (auth.currentUser != null) {
                    val intent = Intent(it, AddArticleActivity::class.java)
                    startActivity(intent)
                } else {
                    Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        articleDB.addChildEventListener(listener)

    }

    override fun onResume() {
        super.onResume()

        articleAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        articleDB.removeEventListener(listener)
    }
}