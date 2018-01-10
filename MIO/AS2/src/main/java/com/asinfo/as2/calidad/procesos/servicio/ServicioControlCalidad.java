package com.asinfo.as2.calidad.procesos.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.DetallePedidoProveedor;
import com.asinfo.as2.entities.PedidoProveedor;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.RegistroPeso;
import com.asinfo.as2.entities.calidad.VariableCalidadOrdenFabricacion;
import com.asinfo.as2.entities.calidad.VariableCalidadProducto;
import com.asinfo.as2.entities.calidad.VariableCalidadRegistroPeso;
import com.asinfo.as2.entities.produccion.HistoricoCalidadOrdenFabricacion;
import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import java.io.InputStream;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioControlCalidad
{
  public abstract List<VariableCalidadRegistroPeso> getListaVariableCalidadRegistroPeso(RegistroPeso paramRegistroPeso);
  
  public abstract List<VariableCalidadProducto> getListaVariableCalidadProducto(Producto paramProducto);
  
  public abstract RegistroPeso cargarExcel(InputStream paramInputStream, RegistroPeso paramRegistroPeso)
    throws ExcepcionAS2;
  
  public abstract void castigar(RegistroPeso paramRegistroPeso, PedidoProveedor paramPedidoProveedor, DetallePedidoProveedor paramDetallePedidoProveedor)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void liberar(RegistroPeso paramRegistroPeso, Bodega paramBodega)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void rechazar(RegistroPeso paramRegistroPeso)
    throws ExcepcionAS2;
  
  public abstract void guardarRegistroPeso(RegistroPeso paramRegistroPeso, EstadoControlCalidad paramEstadoControlCalidad)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract List<VariableCalidadOrdenFabricacion> getListaVariableCalidadOrdenFabricacion(HistoricoCalidadOrdenFabricacion paramHistoricoCalidadOrdenFabricacion);
  
  public abstract HistoricoCalidadOrdenFabricacion cargarExcel(InputStream paramInputStream, HistoricoCalidadOrdenFabricacion paramHistoricoCalidadOrdenFabricacion)
    throws ExcepcionAS2;
  
  public abstract void guardarHistoricoCalidadOrdenFabricacion(HistoricoCalidadOrdenFabricacion paramHistoricoCalidadOrdenFabricacion)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract void generarRecepcionProveedor(RegistroPeso paramRegistroPeso, Bodega paramBodega)
    throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception;
  
  public abstract void cargaListaVariableCalidadOrdenFabricacion(HistoricoCalidadOrdenFabricacion paramHistoricoCalidadOrdenFabricacion);
  
  public abstract void actualizaValorNIRAceptable(VariableCalidadOrdenFabricacion paramVariableCalidadOrdenFabricacion);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.calidad.procesos.servicio.ServicioControlCalidad
 * JD-Core Version:    0.7.0.1
 */