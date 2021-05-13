package com.example.searchplacesapp.Model

import java.util.ArrayList

data class MainPojo (
    val predictions: List<Prediction>,
    val status: String
)

data class Prediction (
    val description: String,
    val matched_substrings: List<MatchedSubstring>,
    val place_id: String,
    val reference: String,
    val structured_formatting: StructuredFormatting,
    val terms: List<Term>,
    val types: List<String>
)

data class MatchedSubstring (
    val length: Long,
    val offset: Long
)

data class StructuredFormatting (
    val main_text: String,
    val main_text_matched_substrings: List<MatchedSubstring>,
    val secondary_text: String
)

data class Term (
    val offset: Long,
    val value: String
)