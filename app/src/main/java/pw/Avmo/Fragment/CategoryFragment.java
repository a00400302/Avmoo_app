package pw.Avmo.Fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pw.Avmo.Adapter.CategoryAdapter;
import pw.Avmo.Bean.CategoryBean;
import pw.Avmo.R;
import pw.Avmo.Source;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {
    private CategoryFragmentHandler handler;
    RecyclerView categoryBodyTypeItem;
    RecyclerView categoryCharacterItem;
    RecyclerView categoryCostumeItem;
    RecyclerView categoryGenreItem;
    RecyclerView categorySexActseItem;
    RecyclerView categorySexPlaysItem;
    RecyclerView categoryThemeItem;
    private String url = "https://avmo.pw/tw/genre";
    private View view;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        return new CategoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);

        // Inflate the layout for this fragment
        categoryBodyTypeItem = (RecyclerView) view.findViewById(R.id.category_Body_Type_item);
        categoryCharacterItem = (RecyclerView) view.findViewById(R.id.category_Character_item);
        categoryCostumeItem = (RecyclerView) view.findViewById(R.id.category_Costume_item);
        categoryGenreItem = (RecyclerView) view.findViewById(R.id.category_Genre_item);
        categorySexActseItem = (RecyclerView) view.findViewById(R.id.category_Sex_Actse_item);
        categorySexPlaysItem = (RecyclerView) view.findViewById(R.id.category_Sex_Plays_item);
        categoryThemeItem = (RecyclerView) view.findViewById(R.id.category_Theme_item);


        handler = new CategoryFragmentHandler();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        CategoryFragmentThread thread = new CategoryFragmentThread();
        thread.start();
        GridLayoutManager layoutManager = new ScrollGridLayoutManager(getActivity(), 3);
        categoryBodyTypeItem.setLayoutManager(layoutManager);
        GridLayoutManager layoutManager1 = new ScrollGridLayoutManager(getActivity(), 3);
        categoryCharacterItem.setLayoutManager(layoutManager1);
        GridLayoutManager layoutManager2 = new ScrollGridLayoutManager(getActivity(), 3);
        categoryCostumeItem.setLayoutManager(layoutManager2);
        GridLayoutManager layoutManager3 = new ScrollGridLayoutManager(getActivity(), 3);
        categoryGenreItem.setLayoutManager(layoutManager3);
        GridLayoutManager layoutManager4 = new ScrollGridLayoutManager(getActivity(), 3);
        categorySexActseItem.setLayoutManager(layoutManager4);
        GridLayoutManager layoutManager5 = new ScrollGridLayoutManager(getActivity(), 3);
        categorySexPlaysItem.setLayoutManager(layoutManager5);
        GridLayoutManager layoutManager6 = new ScrollGridLayoutManager(getActivity(), 3);
        categoryThemeItem.setLayoutManager(layoutManager6);
    }

    public class CategoryFragmentThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Document document = Jsoup.connect("https://avmo.pw/tw/genre").get();
                Message message = handler.obtainMessage();
                message.obj = Source.getCategory(document);
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class CategoryFragmentHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<CategoryBean> list = (List<CategoryBean>) msg.obj;
            List<CategoryBean> themeList = list.subList(0, 62);
            List<CategoryBean> characterList = list.subList(62, 101);
            List<CategoryBean> costumeList = list.subList(101, 134);
            List<CategoryBean> bodyTypeList = list.subList(134, 151);
            List<CategoryBean> sexActseList = list.subList(151, 178);
            List<CategoryBean> sexPlaysList = list.subList(178, 202);
            List<CategoryBean> genreList = list.subList(202, list.size());
            categoryThemeItem.setAdapter(new CategoryAdapter(themeList));
            categoryCharacterItem.setAdapter(new CategoryAdapter(characterList));
            categoryCostumeItem.setAdapter(new CategoryAdapter(costumeList));
            categoryBodyTypeItem.setAdapter(new CategoryAdapter(bodyTypeList));
            categorySexActseItem.setAdapter(new CategoryAdapter(sexActseList));
            categorySexPlaysItem.setAdapter(new CategoryAdapter(sexPlaysList));
            categoryGenreItem.setAdapter(new CategoryAdapter(genreList));

        }
    }



    public class ScrollGridLayoutManager extends GridLayoutManager{
        //        private boolean isScrollEnabled = true;
        public ScrollGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        ScrollGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

        public ScrollGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
            super(context, spanCount, orientation, reverseLayout);
        }
//
//        public  void setScrollEnabled(boolean flag){
//            this.isScrollEnabled = flag;
//        }

        @Override
        public boolean canScrollVertically() {
//            return super.canScrollVertically();
            return false;
        }
    }
}
