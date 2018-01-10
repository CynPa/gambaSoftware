package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.clases.FactorConversion;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.Unidad;
import com.asinfo.as2.entities.UnidadConversion;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioUnidadConversion
{
  public abstract UnidadConversion getUnidadConversion(int paramInt1, int paramInt2);
  
  public abstract List<UnidadConversion> obtenerUnidadConversionConProducto();
  
  public abstract List<UnidadConversion> obtenerUnidadConversionConSubcategoriaProducto();
  
  public abstract List<UnidadConversion> obtenerUnidadConversionSinProductoSubcategoriaProducto();
  
  public abstract FactorConversion obtenerFactorConversion(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws ExcepcionAS2Inventario;
  
  public abstract void actualizaUnidadConversion(UnidadConversion paramUnidadConversion);
  
  public abstract List<Unidad> obtenerListaUnidadConversionPorProducto(int paramInt1, int paramInt2, int paramInt3)
    throws ExcepcionAS2;
  
  public abstract void cargarListaUnidadConversion(Producto paramProducto)
    throws ExcepcionAS2;
  
  public abstract List<UnidadConversion> obtenerUnidadConversionConProducto(Producto paramProducto);
  
  public abstract void actualizaUnidadConversionProducto(Producto paramProducto);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion
 * JD-Core Version:    0.7.0.1
 */