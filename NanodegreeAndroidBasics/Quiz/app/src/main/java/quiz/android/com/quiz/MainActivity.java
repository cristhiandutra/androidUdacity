package quiz.android.com.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CheckBox mQuestion1AnswerA;
    private CheckBox mQuestion1AnswerB;
    private CheckBox mQuestion1AnswerC;

    private RadioButton mQuestion2AnswerA;
    private RadioButton mQuestion3AnswerC;
    private RadioButton mQuestion4AnswerA;
    private EditText mQuestion5Answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestion1AnswerA = findViewById(R.id.question1AnswerA);
        mQuestion1AnswerB = findViewById(R.id.question1AnswerB);
        mQuestion1AnswerC = findViewById(R.id.question1AnswerC);

        mQuestion2AnswerA = findViewById(R.id.question2AnswerA);
        mQuestion3AnswerC = findViewById(R.id.question3AnswerC);
        mQuestion4AnswerA = findViewById(R.id.question4AnswerA);
        mQuestion5Answer = findViewById(R.id.question5Answer);
    }

    /**
     * Correct answer to Question 1 is A and C
     * @return
     */
    private boolean questionOneIsCorrect() {
        return mQuestion1AnswerA.isChecked() && mQuestion1AnswerC.isChecked() && !mQuestion1AnswerB.isChecked();
    }

    /**
     * Correct answer to Question 2 is A
     * @return
     */
    private boolean questionTwoIsCorrect() {
        return mQuestion2AnswerA.isChecked();
    }

    /**
     * Correct answer to Question 3 is C
     * @return
     */
    private boolean questionThreeIsCorrect() {
        return mQuestion3AnswerC.isChecked();
    }

    /**
     * Correct answer to Question 4 is A
     * @return
     */
    private boolean questionFourIsCorrect() {
        return mQuestion4AnswerA.isChecked();
    }

    /**
     * Correct answer is Argentina and Germany
     * @return
     */
    private boolean questionFiveIsCorrect() {
        return mQuestion5Answer.getText().toString().toLowerCase().contains(getString(R.string.argentina)) && mQuestion5Answer.getText().toString().toLowerCase().contains(getString(R.string.germany));
    }

    public void verifyQuestions(View view) {
        if (questionOneIsCorrect()
                && questionTwoIsCorrect()
                && questionThreeIsCorrect()
                && questionFourIsCorrect()
                && questionFiveIsCorrect()) {
            showAnswer(getString(R.string.answers_corrects));
        } else {
            showAnswer(getString(R.string.answers_incorrects));
        }
    }

    private void showAnswer(String answer) {
        Toast.makeText(this, answer, Toast.LENGTH_SHORT).show();
    }
}
