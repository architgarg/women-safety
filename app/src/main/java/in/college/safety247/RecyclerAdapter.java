package in.college.safety247;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charmy Garg on 09-Oct-16.
 */
public class RecyclerAdapter extends AppCompatActivity {

    private List<DatabaseModel> dbList;
    private CardViewAdapter mAdapter;
    static Context context;
    private ContactsDatabaseAdapter mContactsDatabaseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        mContactsDatabaseAdapter =new ContactsDatabaseAdapter(this);
        dbList= new ArrayList<DatabaseModel>();
        dbList = mContactsDatabaseAdapter.getDataFromDB();

        OnItemTouchListener itemTouchListener = new OnItemTouchListener() {
            @Override
            public void onCardViewTap(View view, int position) {
                Toast.makeText(RecyclerAdapter.this, "Tapped " + dbList.get(position), Toast.LENGTH_SHORT).show();
            }

        };


        mAdapter = new CardViewAdapter(dbList, itemTouchListener);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(recyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipeLeft(int position) {
                                return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                    Toast.makeText(MainActivity.this, mItems.get(position) + " swiped left", Toast.LENGTH_SHORT).show();
                                    String pos = Integer.toString(position);
                                    dbList.remove(position);
                                    mContactsDatabaseAdapter.deleteARow(pos);
                                    mAdapter.notifyItemRemoved(position);
                                }
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                    Toast.makeText(MainActivity.this, mItems.get(position) + " swiped right", Toast.LENGTH_SHORT).show();
                                    dbList.remove(position);
                                    String pos = Integer.toString(position);
                                    mContactsDatabaseAdapter.deleteARow(pos);
                                    mAdapter.notifyItemRemoved(position);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        });

        recyclerView.addOnItemTouchListener(swipeTouchListener);

    }

    public interface OnItemTouchListener {
        /**
         * Callback invoked when the user Taps one of the RecyclerView items
         *
         * @param view     the CardView touched
         * @param position the index of the item touched in the RecyclerView
         */
        void onCardViewTap(View view, int position);
        /**
         * Callback invoked when the Button1 of an item is touched
         *
         * @param view     the Button touched
         * @param position the index of the item touched in the RecyclerView
         */
    }

    public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
        private List<DatabaseModel> dbList;
        private OnItemTouchListener onItemTouchListener;

        public CardViewAdapter(List<DatabaseModel> dbList, OnItemTouchListener onItemTouchListener) {
            this.dbList = dbList;
            this.onItemTouchListener = onItemTouchListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_row, parent, false);

            // create ViewHolder

            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.name.setText(dbList.get(position).getName());
            holder.number.setText(dbList.get(position).getNumber());
        }

        @Override
        public int getItemCount() {
            return dbList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView name, number;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                name = (TextView) itemLayoutView
                        .findViewById(R.id.rvname);
                number = (TextView) itemLayoutView.findViewById(R.id.rvnum);
                itemLayoutView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {

                onItemTouchListener.onCardViewTap(v, getLayoutPosition());
                Toast.makeText(RecyclerAdapter.context, "you have clicked Row " + getAdapterPosition(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
