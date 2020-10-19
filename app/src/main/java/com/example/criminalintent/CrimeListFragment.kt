package com.example.criminalintent

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.R
import java.text.DateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import javax.security.auth.callback.Callback

private const val TAG = "CrimeListFragment"
private lateinit var crimeRecyclerView: RecyclerView



class CrimeListFragment : Fragment() {

    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())


    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }
    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    var callBacks:CallBacks?=null
    interface CallBacks{
        fun onItemSelected(crimeId: UUID)
    }

    override fun onDetach() {
        super.onDetach()
        callBacks=null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBacks=context as CallBacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list,
            container, false)
        crimeRecyclerView =
            view.findViewById(R.id.crime_recycler_view) as
                    RecyclerView
        crimeRecyclerView.layoutManager =
            LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState:
    Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimesListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            })
    }




    private fun updateUI(crimes: List<Crime>) {
        //val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }



    private inner class CrimeAdapter(var crimes: List<Crime>)
        : RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int)
                : CrimeHolder {
            val view =
                layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }
        override fun getItemCount() = crimes.size
        override fun onBindViewHolder(holder: CrimeHolder,
                                      position: Int) {
            val crime = crimes[position]
            holder.apply {
                titleTextView.text = crime.title
                dateTextView.text = crime.date.toString()

            }
        }
    }


    private inner class CrimeHolder(view: View)
        : RecyclerView.ViewHolder(view) ,View.OnClickListener{
        private val solvedImageView: ImageView =
            itemView.findViewById(R.id.crime_solved)

        val titleTextView: TextView =
            itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView =
            itemView.findViewById(R.id.crime_date)



        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

        }

    }

}


