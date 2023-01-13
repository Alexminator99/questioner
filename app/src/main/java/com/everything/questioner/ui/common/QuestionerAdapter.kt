package com.everything.questioner.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.everything.questioner.data.models.InterestingFact
import com.everything.questioner.data.models.MotivationalPhrase
import com.everything.questioner.data.models.Question
import com.everything.questioner.databinding.InterestingFactItemBinding
import com.everything.questioner.databinding.MotivationalPhraseItemBinding
import com.everything.questioner.databinding.QuestionerItemBinding
import com.everything.questioner.utils.AnswerType

class QuestionerAdapter(
    private val questionList: List<Any>,
    private val listener: OnButtonClickListener
) : RecyclerView.Adapter<QuestionerAdapter.QuestionerViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (questionList[position]) {
            is MotivationalPhrase -> 0
            is Question -> 1
            else -> -1
        }
    }

    class QuestionerViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Any, listener: OnButtonClickListener) {
            when (item) {
                is MotivationalPhrase -> {
                    val motivationalPhraseItemBinding = binding as MotivationalPhraseItemBinding
                    motivationalPhraseItemBinding.motivationalPhrase = item
                    motivationalPhraseItemBinding.executePendingBindings()
                }
                is Question -> {
                    val questionerItemBinding = binding as QuestionerItemBinding
                    questionerItemBinding.question = item
                    questionerItemBinding.executePendingBindings()
                    questionerItemBinding.siButton.setOnClickListener {
                        listener.onButtonClick(
                            item.statement,
                            AnswerType.YES
                        )
                    }
                    questionerItemBinding.noButton.setOnClickListener {
                        listener.onButtonClick(
                            item.statement,
                            AnswerType.NO
                        )
                    }
                    questionerItemBinding.maybeButton.setOnClickListener {
                        listener.onButtonClick(
                            item.statement,
                            AnswerType.MAYBE
                        )
                    }
                }
                is InterestingFact -> {
                    val interestingFactItemBinding = binding as InterestingFactItemBinding
                    interestingFactItemBinding.interestingFact = item
                    interestingFactItemBinding.animationView.setAnimation(curiosityAnimations.random())
                    interestingFactItemBinding.executePendingBindings()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionerViewHolder {

        when (viewType) {
            0 -> {
                val binding = MotivationalPhraseItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return QuestionerViewHolder(binding)
            }
            1 -> {
                val binding = QuestionerItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return QuestionerViewHolder(binding)
            }
            else -> {
                val binding = InterestingFactItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return QuestionerViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: QuestionerViewHolder, position: Int) {
        holder.bind(questionList[position], listener)
    }

    override fun getItemCount() = questionList.size

    interface OnButtonClickListener {
        fun onButtonClick(question: String, buttonType: AnswerType)
    }

    companion object {
        val curiosityAnimations = listOf(
            "question_mark.json",
            "question_mark1.json",
            "question_mark2.json",
            "test.json",
        )
    }
}
