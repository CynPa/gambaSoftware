package com.asinfo.as2.financiero.SRI.procesos.servicio;

import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.sri.FacturaClienteSRI;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.util.List;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioFacturaClienteSRIRemote
{
  public abstract void guardar(FacturaClienteSRI paramFacturaClienteSRI);
  
  public abstract void eliminar(FacturaClienteSRI paramFacturaClienteSRI);
  
  public abstract FacturaClienteSRI buscarPorId(int paramInt);
  
  public abstract List<FacturaClienteSRI> obtenerFacturasMes(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void eliminarFacturaClienteSRI(int paramInt);
  
  public abstract List<FacturaClienteSRI> obtenerValoresRetenidosMes(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract FacturaCliente actualizarFacturaClienteSRI(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2;
  
  public abstract void actualizarDatosExportacion(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRIRemote
 * JD-Core Version:    0.7.0.1
 */