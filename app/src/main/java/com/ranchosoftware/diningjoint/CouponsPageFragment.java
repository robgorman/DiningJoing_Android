package com.ranchosoftware.diningjoint;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ranchosoftware.diningjoint.Model.Coupon;
import com.ranchosoftware.diningjoint.Server.Server;
import com.sandiegorestaurantsandbars.diningjoint.R;


public class CouponsPageFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  public static final String ARG_PAGE = "page";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;

  private int pageNumber;


  public static CouponsPageFragment create(int pageNumber) {
    CouponsPageFragment fragment = new CouponsPageFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_PAGE, pageNumber);
    fragment.setArguments(args);
    return fragment;
  }

  public CouponsPageFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      pageNumber = getArguments().getInt(ARG_PAGE);
    }


  }



  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_coupons_page, container, false);

    DiningJointActivity activity = (DiningJointActivity) getActivity();
    Coupon coupon = activity.getModel().getSelectedRestaurant().getCoupons().get(pageNumber);

    final ImageView ivCoupon = (ImageView) v.findViewById(R.id.ivCoupon);


    Server.getInstance().getCouponImage(coupon.getImageUrl(), new Server.ImageSuccessCallback() {
      @Override
      public void success(Bitmap bitmap) {

        ivCoupon.setImageBitmap(bitmap);

      }
    }, new Server.FailureCallback() {
      @Override
      public void failure(String message) {

        // TODO: some other image
      }
    });

    TextView tvRotate = (TextView) v.findViewById(R.id.tvRotate);
    if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
      tvRotate.setVisibility(View.INVISIBLE);
    } else {
      tvRotate.setVisibility(View.VISIBLE);
    }

    return v;
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

//  @Override
//  public void onAttach(Activity activity) {
//    super.onAttach(activity);
//    try {
//      mListener = (OnFragmentInteractionListener) activity;
//    } catch (ClassCastException e) {
//      throw new ClassCastException(activity.toString()
//              + " must implement OnFragmentInteractionListener");
//    }
//  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    public void onFragmentInteraction(Uri uri);
  }

}
