package com.example.fragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class BlankFragment : Fragment() , InputStudentDialog.Callbacks {
    override fun onStudentAdd(student: Student)
    {
        studentListView.addStudent(student)
        updateUI()

    }

    override fun onStudentDelete(position: Int)
    {
        studentListView.deleteStudent(position)
        updateUI()

    }

    private lateinit var stuRecyclerView: RecyclerView
    private var adapter:  StudentAdapter? = null
    private lateinit var noData: TextView
    private val studentListView: StudentListViewModel by lazy {
        ViewModelProviders.of(this).get(StudentListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_blank, container, false)

        stuRecyclerView =
            view.findViewById(R.id.stu_recycler_view) as RecyclerView
        stuRecyclerView.layoutManager = LinearLayoutManager(context)

        noData = view.findViewById(R.id.empty_list_tv) as TextView
        updateUI()
        return view
    }
    private fun updateUI() {
        val student = studentListView.students
        adapter = StudentAdapter(student)
        stuRecyclerView.adapter = adapter




    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragmeny_blank, menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_student -> {
                InputStudentDialog.newInstance().apply {
                    setTargetFragment(this@BlankFragment,0)
                    show(this@BlankFragment.requireFragmentManager(),"Input")
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }



    private inner class StudentHolder(view: View) : RecyclerView.ViewHolder(view) , View.OnClickListener {



        private lateinit var student: Student

        val nameTextView: TextView = itemView.findViewById(R.id.stu_name)
        val passTextView: TextView = itemView.findViewById(R.id.stu_pass)
        var deleteBtn : Button = itemView.findViewById(R.id.delete_btn)


            init {
                     deleteBtn.setOnClickListener(this)
            }

        fun bind(student: Student) {
            this.student = student
            nameTextView.text = this.student.studName
            passTextView.text = this.student.stuPass.toString()


        }



        override fun onClick(v: View?)
        {
            onStudentDelete(adapterPosition)
        }
    }

        private inner class StudentAdapter(var students: List<Student>) :
            RecyclerView.Adapter<StudentHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                    : StudentHolder {
                val view = layoutInflater.inflate(R.layout.stu_list_item, parent, false)
                return StudentHolder(view)
            }

            override fun getItemCount() :Int
            {
                if (students.isEmpty())
                    noData.visibility=View.VISIBLE
                else
                    noData.visibility= View.GONE
                return students.size
            }


            override fun onBindViewHolder(holder: StudentHolder, position: Int) {
                val student = students[position]
                holder.bind(student)
            }


        }

}
