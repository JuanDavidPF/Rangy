package com.example.rangy;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class modalnferior extends BottomSheetDialogFragment {

    private clickListener mListener;
    private TextView producto, cantidad, reference, app;
    private ImageView cantidadImagen;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmento = inflater.inflate(R.layout.modal_inferior, container, false);

        Bundle args = getArguments();
        final String productTitle = args.getString("producto", "Recurso");


        Button btn_quiero = fragmento.findViewById(R.id.quiero_btn);
        producto = fragmento.findViewById(R.id.producto);
        cantidadImagen = fragmento.findViewById(R.id.cantidadImagen);
        cantidad = fragmento.findViewById(R.id.cantidad);
        reference = fragmento.findViewById(R.id.reference);
        app = fragmento.findViewById(R.id.apps);


        switch (productTitle) {
            case "Tablet":
                cantidadImagen.setImageResource(R.drawable.cantidadtablet);
                String tabletQuantity = args.getString("cantidadTablets", "0");
                cantidad.setText(tabletQuantity);
                reference.setText("Samsung Galaxy A12");
                app.setText("Apps de utilidad, G-suite");
                break;

            case "Macbook":
                cantidadImagen.setImageResource(R.drawable.cantidadlaptop);
                String laptopQuantity = args.getString("cantidadPortatiles", "0");
                cantidad.setText(laptopQuantity);
                reference.setText("Macbook Pro Md 101");
                app.setText("Apps de diseño, iCloud");
                break;

            case "Audifonos":
                cantidadImagen.setImageResource(R.drawable.cantidadheadphones);
                String headphoneQuantity = args.getString("cantidadAudifonos", "0");
                cantidad.setText(headphoneQuantity);
                reference.setText("Geniuset Extra-bass");
                app.setText("Aislamiento de audio");
                break;
        }


        producto.setText(productTitle);


        btn_quiero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.modalBtnClicked(productTitle);

                int quantity = Integer.parseInt(cantidad.getText().toString());

                if (quantity > 0) {
                    dismiss();
                }

            }
        });


        return fragmento;
    }


    public interface clickListener {
        void modalBtnClicked(String producto);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (clickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement clickListener");
        }

    }
}//cierra la clase modalInferior
