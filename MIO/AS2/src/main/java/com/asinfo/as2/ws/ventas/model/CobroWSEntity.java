/*   1:    */ package com.asinfo.as2.ws.ventas.model;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Banco;
/*   4:    */ import com.asinfo.as2.entities.Cobro;
/*   5:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*   6:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   7:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   8:    */ import com.asinfo.as2.entities.DetalleCobro;
/*   9:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*  10:    */ import com.asinfo.as2.entities.Empresa;
/*  11:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  12:    */ import com.asinfo.as2.entities.FormaPago;
/*  13:    */ import java.io.Serializable;
/*  14:    */ import java.math.BigDecimal;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Calendar;
/*  17:    */ import java.util.Iterator;
/*  18:    */ import java.util.List;
/*  19:    */ 
/*  20:    */ public class CobroWSEntity
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   private String numero;
/*  25:    */   private String usuario;
/*  26:    */   private Calendar fecha;
/*  27:    */   private String identificacionCliente;
/*  28: 41 */   private BigDecimal valorTotal = BigDecimal.ZERO.setScale(2);
/*  29:    */   private String descripcion;
/*  30:    */   private DetalleCobroWSEntity[] listaDetalleCobro;
/*  31:    */   private DetalleFormaCobroWSEntity[] listaDetalleFormaCobro;
/*  32:    */   
/*  33:    */   public CobroWSEntity() {}
/*  34:    */   
/*  35:    */   public CobroWSEntity(Cobro cobro)
/*  36:    */   {
/*  37: 62 */     this.numero = cobro.getNumero();
/*  38: 63 */     this.fecha = Calendar.getInstance();
/*  39: 64 */     this.fecha.setTime(cobro.getFecha());
/*  40: 65 */     this.identificacionCliente = cobro.getEmpresa().getIdentificacion();
/*  41: 66 */     this.valorTotal = cobro.getValor();
/*  42: 67 */     this.descripcion = cobro.getDescripcion();
/*  43: 68 */     List<DetalleCobroWSEntity> lista = new ArrayList();
/*  44: 70 */     for (Iterator localIterator = cobro.getListaDetalleCobro().iterator(); localIterator.hasNext();)
/*  45:    */     {
/*  46: 70 */       detalle = (DetalleCobro)localIterator.next();
/*  47: 71 */       DetalleCobroWSEntity detalleCobroWS = new DetalleCobroWSEntity();
/*  48: 72 */       detalleCobroWS.setValorCobro(detalle.getValor());
/*  49: 73 */       if ((detalle.getCuentaPorCobrar() != null) && (detalle.getCuentaPorCobrar().getFacturaCliente() != null))
/*  50:    */       {
/*  51: 74 */         detalleCobroWS.setEstablecimientoFactura(detalle.getCuentaPorCobrar().getFacturaCliente().getNumero().substring(0, 3));
/*  52: 75 */         detalleCobroWS.setPuntoVentaFactura(detalle.getCuentaPorCobrar().getFacturaCliente().getNumero().substring(4, 7));
/*  53: 76 */         detalleCobroWS.setNumeroFactura(detalle.getCuentaPorCobrar().getFacturaCliente().getNumero().substring(8));
/*  54:    */       }
/*  55: 78 */       lista.add(detalleCobroWS);
/*  56:    */     }
/*  57:    */     DetalleCobro detalle;
/*  58: 80 */     this.listaDetalleCobro = ((DetalleCobroWSEntity[])lista.toArray(new DetalleCobroWSEntity[0]));
/*  59:    */     
/*  60: 82 */     Object listaFormaCobro = new ArrayList();
/*  61: 83 */     for (DetalleFormaCobro formaCobro : cobro.getListaDetalleFormaCobro())
/*  62:    */     {
/*  63: 84 */       DetalleFormaCobroWSEntity detalleFormaCobro = new DetalleFormaCobroWSEntity();
/*  64: 85 */       detalleFormaCobro.setBancoOrigen(formaCobro.getBanco().getCodigo());
/*  65: 86 */       detalleFormaCobro.setDocumentoReferencia(formaCobro.getDocumentoReferencia());
/*  66: 87 */       detalleFormaCobro.setFormaPago(formaCobro.getFormaPago().getCodigo());
/*  67: 88 */       detalleFormaCobro.setNumeroCuentaBancariaPago(formaCobro.getCuentaBancariaOrganizacion().getCuentaBancaria().getNumero());
/*  68: 89 */       detalleFormaCobro.setValor(formaCobro.getValor());
/*  69: 90 */       ((List)listaFormaCobro).add(detalleFormaCobro);
/*  70:    */     }
/*  71: 92 */     this.listaDetalleFormaCobro = ((DetalleFormaCobroWSEntity[])((List)listaFormaCobro).toArray(new DetalleFormaCobroWSEntity[0]));
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getNumero()
/*  75:    */   {
/*  76: 97 */     return this.numero;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setNumero(String numero)
/*  80:    */   {
/*  81:101 */     this.numero = numero;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Calendar getFecha()
/*  85:    */   {
/*  86:105 */     return this.fecha;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setFecha(Calendar fecha)
/*  90:    */   {
/*  91:109 */     this.fecha = fecha;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getIdentificacionCliente()
/*  95:    */   {
/*  96:113 */     return this.identificacionCliente;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setIdentificacionCliente(String identificacionCliente)
/* 100:    */   {
/* 101:117 */     this.identificacionCliente = identificacionCliente;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public BigDecimal getValorTotal()
/* 105:    */   {
/* 106:121 */     return this.valorTotal;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setValorTotal(BigDecimal valorTotal)
/* 110:    */   {
/* 111:125 */     this.valorTotal = valorTotal;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getDescripcion()
/* 115:    */   {
/* 116:129 */     return this.descripcion;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setDescripcion(String descripcion)
/* 120:    */   {
/* 121:133 */     this.descripcion = descripcion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public DetalleCobroWSEntity[] getListaDetalleCobro()
/* 125:    */   {
/* 126:137 */     return this.listaDetalleCobro;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setListaDetalleCobro(DetalleCobroWSEntity[] listaDetalleCobro)
/* 130:    */   {
/* 131:141 */     this.listaDetalleCobro = listaDetalleCobro;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String getUsuario()
/* 135:    */   {
/* 136:145 */     return this.usuario;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setUsuario(String usuario)
/* 140:    */   {
/* 141:149 */     this.usuario = usuario;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public DetalleFormaCobroWSEntity[] getListaDetalleFormaCobro()
/* 145:    */   {
/* 146:153 */     return this.listaDetalleFormaCobro;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setListaDetalleFormaCobro(DetalleFormaCobroWSEntity[] listaDetalleFormaCobro)
/* 150:    */   {
/* 151:157 */     this.listaDetalleFormaCobro = listaDetalleFormaCobro;
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.model.CobroWSEntity
 * JD-Core Version:    0.7.0.1
 */