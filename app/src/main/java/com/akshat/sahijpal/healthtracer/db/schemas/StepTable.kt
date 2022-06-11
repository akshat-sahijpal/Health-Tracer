package com.akshat.sahijpal.healthtracer.db.schemas

data class StepTable(
        var steps_taken:Int,
        var coins_earned:Int,
        var ads :Int,
        var daily_limit_ads:Int,
        var milestone: Milestone
)
{
    data class Milestone(
            var safe_zone:Int,
            var upper_limit:Int,
            var increased_limit:Int
    )
}
