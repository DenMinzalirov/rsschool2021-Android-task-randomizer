package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    private var actionListener: OnActionListener? = null
    private var minValue: EditText? = null
    private var maxValue: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    interface OnActionListener {
        fun onSendData(min: Int, max: Int)
        fun onClickInvalid(msg: String)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        actionListener = context as OnActionListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        minValue = view.findViewById(R.id.min_value)
        maxValue = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            val min = minValue?.text.toString().toIntOrNull()
            val max = maxValue?.text.toString().toIntOrNull()

            when {
                min != null && max != null -> when {
                    max < 0 || min < 0 -> actionListener?.onClickInvalid("negative numbers")
                    min > max -> actionListener?.onClickInvalid("max is less than min")
                    else -> actionListener?.onSendData(min, max)
                }
                else -> actionListener?.onClickInvalid("only numbers")
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}

