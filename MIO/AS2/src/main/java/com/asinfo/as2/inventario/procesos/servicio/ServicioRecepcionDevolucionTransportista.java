package com.asinfo.as2.inventario.procesos.servicio;

import com.asinfo.as2.entities.RecepcionDevolucionTransportista;
import com.asinfo.as2.excepciones.AS2Exception;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioRecepcionDevolucionTransportista
{
  public abstract void guardar(RecepcionDevolucionTransportista paramRecepcionDevolucionTransportista)
    throws AS2Exception;
  
  public abstract void eliminar(RecepcionDevolucionTransportista paramRecepcionDevolucionTransportista);
  
  public abstract RecepcionDevolucionTransportista buscarPorId(int paramInt);
  
  public abstract List<RecepcionDevolucionTransportista> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<RecepcionDevolucionTransportista> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract RecepcionDevolucionTransportista cargarDetalle(int paramInt);
  
  public abstract List<Object[]> getReporteRecepcionDevolucionTransportista(Date paramDate, int paramInt, boolean paramBoolean);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.ServicioRecepcionDevolucionTransportista
 * JD-Core Version:    0.7.0.1
 */