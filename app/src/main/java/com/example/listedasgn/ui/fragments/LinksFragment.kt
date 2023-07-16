package com.example.listedasgn.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listedasgn.R
import com.example.listedasgn.factory.ViewModelFactory
import com.example.listedasgn.ui.fragments.adapters.InstantAdapter
import com.example.listedasgn.ui.fragments.adapters.InstantViewModel
import com.example.listedasgn.ui.fragments.adapters.LinkAdapter
import com.example.listedasgn.ui.fragments.adapters.LinkViewModel
import com.example.listedasgn.network.RetroService
import com.example.listedasgn.repository.DataRepository
import com.example.listedasgn.viewmodel.MainActivityViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

class LinksFragment : Fragment() {

    private lateinit var lineChart: LineChart
    private lateinit var instantRV: RecyclerView
    private lateinit var linkRV: RecyclerView
    private lateinit var topLinksButton: TextView
    private lateinit var recentLinksButton: TextView
    private lateinit var whatsappBtn: ConstraintLayout
    private lateinit var adapter: InstantAdapter
    private lateinit var linkAdapter: LinkAdapter
    private var instantList = mutableListOf<InstantViewModel>()
    private var linksList = mutableListOf<LinkViewModel>()
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var greetingTV: TextView
    private val retroService = RetroService.createRetrofitInstance()
    private var monthDataJune = 0
    private var monthDataJuly = 0


    private var xValues = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_links, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        instantRV = view.findViewById(R.id.instant_rv)
        linkRV = view.findViewById(R.id.top_recent_link_rv)
        greetingTV = view.findViewById(R.id.greeting_tv)
        lineChart = view.findViewById(R.id.line_chart)
        topLinksButton = view.findViewById(R.id.top_links_button)
        recentLinksButton = view.findViewById(R.id.recent_links_button)
        whatsappBtn = view.findViewById(R.id.whatsapp_cl)
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(DataRepository(retroService))
        )[MainActivityViewModel::class.java]


        viewModel.getResultData("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI")
        instantRV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        linkRV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        greetingTV.text = getGreetingMessage()

        viewModel.result.observe(requireActivity(), Observer {
                val activity: FragmentActivity? = activity
                if(isAdded && activity !=null) {
                setInstantLinks()
                setTopLinks()
                setLineChart()
                }

        })
        whatsappBtn.setOnClickListener { openWhatsappIntent() }
        topLinksButton.setOnClickListener { setTopLinks() }
        recentLinksButton.setOnClickListener { setRecentLinks() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setLineChart() {
        val activity: FragmentActivity? = activity
        if(isAdded && activity !=null) {
            viewModel.result.observe(requireActivity(), Observer {
                monthDataJune =
                    it.data.overall_url_chart.`2023-06-08` +
                            it.data.overall_url_chart.`2023-06-09` +
                            it.data.overall_url_chart.`2023-06-10` +
                            it.data.overall_url_chart.`2023-06-11` +
                            it.data.overall_url_chart.`2023-06-12` +
                            it.data.overall_url_chart.`2023-06-13` +
                            it.data.overall_url_chart.`2023-06-14` +
                            it.data.overall_url_chart.`2023-06-15` +
                            it.data.overall_url_chart.`2023-06-16` +
                            it.data.overall_url_chart.`2023-06-17` +
                            it.data.overall_url_chart.`2023-06-18` +
                            it.data.overall_url_chart.`2023-06-19` +
                            it.data.overall_url_chart.`2023-06-20` +
                            it.data.overall_url_chart.`2023-06-21` +
                            it.data.overall_url_chart.`2023-06-22` +
                            it.data.overall_url_chart.`2023-06-23` +
                            it.data.overall_url_chart.`2023-06-24` +
                            it.data.overall_url_chart.`2023-06-25` +
                            it.data.overall_url_chart.`2023-06-26` +
                            it.data.overall_url_chart.`2023-06-27` +
                            it.data.overall_url_chart.`2023-06-28`
                monthDataJuly =
                    it.data.overall_url_chart.`2023-07-06` + it.data.overall_url_chart.`2023-07-05` + it.data.overall_url_chart.`2023-07-06` +it.data.overall_url_chart.`2023-07-08` +
                it.data.overall_url_chart.`2023-07-04` + it.data.overall_url_chart.`2023-07-01` + it.data.overall_url_chart.`2023-07-02` + it.data.overall_url_chart.`2023-07-03`
                val description = Description()
                description.text = "Total Clicks"
                description.setPosition(150f, 15f)
                lineChart.description = description
                lineChart.axisRight.setDrawLabels(false)


                val xAxis = lineChart.xAxis
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
                xAxis.labelCount = 12
                xAxis.granularity = 1f


                val yAxis = lineChart.axisLeft
                if (monthDataJuly > monthDataJune) {
                    yAxis.axisMaximum = monthDataJuly +20f
                } else {
                    yAxis.axisMaximum = monthDataJune + 20f
                }
                yAxis.granularity = 10f
                yAxis.axisMinimum = 0f
                yAxis.axisLineColor = Color.BLACK
                yAxis.labelCount = 12
                val entries: MutableList<Entry> = mutableListOf()
                entries.add(Entry(0f, 0f))
                entries.add(Entry(9f, 0f))
                entries.add(Entry(10f, monthDataJune.toFloat()))
                entries.add(Entry(11f, monthDataJuly.toFloat()))
                lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(getMonthsLastYear())
                val lineDataSet = LineDataSet(entries, "Clicks")
                lineDataSet.color = Color.BLUE
                lineDataSet.valueTextSize = 14f

                lineDataSet.setDrawFilled(true)
                val lineData = LineData(lineDataSet)
                lineChart.data = lineData
                lineChart.invalidate()
            })
        }
    }

    private fun openWhatsappIntent() {
        val activity: FragmentActivity? = activity
        if(isAdded && activity!=null) {
            viewModel.result.observe(requireActivity(), Observer {
                val uri = Uri.parse("smsto:" + "+91" + it.support_whatsapp_number)
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                intent.setPackage("com.whatsapp")
                if(isAdded) {
                    if (intent.resolveActivity(requireActivity().packageManager) != null) {
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Package not available",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })
        }
    }

    private fun setInstantLinks(){
        val activity: FragmentActivity? = activity
        if(isAdded && activity!=null){
            viewModel.result.observe(requireActivity(), Observer {
                instantList.clear()
                instantList.add(
                    InstantViewModel(
                        R.drawable.total_clicks,
                        (it.total_clicks).toString(),
                        "Total Clicks"
                    )
                )
                instantList.add(
                    InstantViewModel(
                        R.drawable.today_clicks,
                        (it.today_clicks).toString(),
                        "Today Clicks"
                    )
                )
                instantList.add(
                    InstantViewModel(
                        R.drawable.baseline_link_24,
                        (it.total_links).toString(),
                        "Total Links"
                    )
                )
                instantList.add(
                    InstantViewModel(
                        R.drawable.top_location,
                        it.top_location.let { if (it == "") "N/A" else it },
                        "Top Location"
                    )
                )
                instantList.add(
                    InstantViewModel(
                        R.drawable.top_source,
                        it.top_source.let { if (it == "") "N/A" else it },
                        "Top Source"
                    )
                )
                instantList.add(
                    InstantViewModel(
                        R.drawable.baseline_add_24,
                        it.startTime.let { if (it == "") "N/A" else it },
                        "Start Time"
                    )
                )
            })
            adapter = InstantAdapter(requireActivity(), instantList)
            instantRV.adapter = adapter
        }
    }

    private fun setTopLinks() {


        val activity: FragmentActivity? = activity
        if(isAdded && activity!=null){
            topLinksButton.setTextColor(Color.parseColor("#FFFFFF"))
            topLinksButton.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.edit_text_background)
            recentLinksButton.setTextColor(Color.parseColor("#999CA0"))
            recentLinksButton.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.edit_text_background_unselected
            )
            viewModel.result.observe(requireActivity(), Observer {
                linksList.clear()
                val topLinks = it.data.top_links

                if(topLinks.size >= 4){
                    for (i in 0 ..3){
                        linksList.add(LinkViewModel(R.drawable.amazon,topLinks[i].title,topLinks[i].smart_link,topLinks[i].created_at,topLinks[i].total_clicks))
                    }
                }
                else{
                    for (i in 0 ..topLinks.size-1){
                        linksList.add(LinkViewModel(R.drawable.amazon,topLinks[i].title,topLinks[i].smart_link,topLinks[i].created_at,topLinks[i].total_clicks))
                    }
                }
            })
            linkAdapter = LinkAdapter(requireActivity(), linksList)
            linkAdapter
            linkRV.adapter = linkAdapter
        }

    }

    private fun setRecentLinks() {
        val activity: FragmentActivity? = activity
        if(isAdded && activity!=null){
            recentLinksButton.setTextColor(Color.parseColor("#FFFFFF"))
            topLinksButton.setTextColor(Color.parseColor("#999CA0"))
            topLinksButton.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.edit_text_background_unselected
            )
            recentLinksButton.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.edit_text_background)
            viewModel.result.observe(requireActivity(), Observer {
                val recentLinks = it.data.recent_links
                linksList.clear()
                if(recentLinks.size >= 4){
                    for (i in 0 ..3){
                        linksList.add(LinkViewModel(R.drawable.amazon,recentLinks[i].title,recentLinks[i].smart_link,recentLinks[i].created_at,recentLinks[i].total_clicks))
                    }
                }
                else{
                    for (i in 0 ..recentLinks.size-1){
                        linksList.add(LinkViewModel(R.drawable.amazon,recentLinks[i].title,recentLinks[i].smart_link,recentLinks[i].created_at,recentLinks[i].total_clicks))
                    }
                }
            })
            linkAdapter = LinkAdapter(requireActivity(), linksList)
            linkRV.adapter = linkAdapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getMonthsLastYear(): List<String> {
        val months = mutableListOf<String>()
        val currentDate = LocalDate.now()

        for (i in 11 downTo 0) {
            val month = currentDate.minusMonths(i.toLong()).month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            months.add(month)
        }

        return months
    }

    private fun getGreetingMessage(): String {
        val currentTime = Calendar.getInstance()

        val greetingMessage = when (currentTime.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            else -> "Good Evening"
        }

        return greetingMessage
    }
}