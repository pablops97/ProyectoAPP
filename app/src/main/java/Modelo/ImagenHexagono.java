package Modelo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class ImagenHexagono extends AppCompatImageView {
    private Path hexagonPath;

    public ImagenHexagono(Context context) {
        super(context);
        init();
    }

    public ImagenHexagono(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImagenHexagono(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        hexagonPath = new Path();
        // Define your hexagon path here based on the XML path data
        hexagonPath.moveTo(0, 0);
        hexagonPath.lineTo(726, 0);
        hexagonPath.lineTo(726, 628);
        hexagonPath.lineTo(0, 628);
        hexagonPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(hexagonPath, Region.Op.INTERSECT);
        super.onDraw(canvas);
    }
}