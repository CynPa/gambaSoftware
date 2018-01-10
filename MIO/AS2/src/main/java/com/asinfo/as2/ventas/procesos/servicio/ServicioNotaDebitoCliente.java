package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioNotaDebitoCliente
{
  public abstract FacturaCliente guardar(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas, ExcepcionAS2Inventario, ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void eliminar(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2;
  
  public abstract long verificaExistenciaNumero(FacturaCliente paramFacturaCliente);
  
  public abstract List getReporteNotaDebitoCliente(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioNotaDebitoCliente
 * JD-Core Version:    0.7.0.1
 */