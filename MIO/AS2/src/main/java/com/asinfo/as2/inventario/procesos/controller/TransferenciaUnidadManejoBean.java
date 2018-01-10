/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.DetalleMovimientoUnidadManejo;
/*   4:    */ import com.asinfo.as2.entities.Documento;
/*   5:    */ import com.asinfo.as2.entities.MovimientoUnidadManejo;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.SaldoUnidadManejo;
/*   8:    */ import com.asinfo.as2.entities.Transportista;
/*   9:    */ import com.asinfo.as2.entities.UnidadManejo;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  14:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoUnidadManejo;
/*  15:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.annotation.PostConstruct;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import javax.faces.event.ActionEvent;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class TransferenciaUnidadManejoBean
/*  29:    */   extends AjusteUnidadManejoBean
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = -5463907651730263568L;
/*  32:    */   
/*  33:    */   @PostConstruct
/*  34:    */   public void init()
/*  35:    */   {
/*  36: 48 */     super.init();
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected void verificarTipoMovimiento()
/*  40:    */   {
/*  41: 52 */     if (this.servicioTransportista.verificarTransportista(AppUtil.getOrganizacion().getId(), AppUtil.getUsuarioEnSesion().getNombreUsuario()))
/*  42:    */     {
/*  43: 53 */       setIndicadorTransportista(true);
/*  44: 54 */       setIndicadorSucursal(false);
/*  45:    */     }
/*  46:    */     else
/*  47:    */     {
/*  48: 56 */       setIndicadorTransportista(false);
/*  49: 57 */       setIndicadorSucursal(true);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Map<String, String> getFiltros(Map<String, String> filters)
/*  54:    */   {
/*  55: 62 */     filters.put("indicadorAjusteUnidadManejo", "false");
/*  56: 63 */     if (isIndicadorTransportista()) {
/*  57: 64 */       filters.put("transportista.usuario.nombreUsuario", AppUtil.getUsuarioEnSesion().getNombreUsuario());
/*  58:    */     }
/*  59: 66 */     return filters;
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected void crearMovimientoUnidadManejo()
/*  63:    */   {
/*  64: 71 */     super.crearMovimientoUnidadManejo();
/*  65: 72 */     this.movimientoUnidadManejo.setIndicadorAjusteUnidadManejo(false);
/*  66: 73 */     if (isIndicadorSucursal()) {
/*  67: 75 */       this.movimientoUnidadManejo.setSucursal(AppUtil.getSucursal());
/*  68:    */     }
/*  69: 77 */     this.movimientoUnidadManejo.setEstado(Estado.ELABORADO);
/*  70: 78 */     if (isIndicadorTransportista())
/*  71:    */     {
/*  72: 79 */       this.movimientoUnidadManejo.setEstado(Estado.PROCESADO);
/*  73: 80 */       for (Documento doc : getListaDocumentosAjuste()) {
/*  74: 81 */         if (doc.getOperacion() == -1) {
/*  75: 82 */           this.movimientoUnidadManejo.setDocumento(doc);
/*  76:    */         }
/*  77:    */       }
/*  78: 85 */       actualizarTransportista();
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public DetalleMovimientoUnidadManejo agregarDetalle()
/*  83:    */   {
/*  84: 91 */     DetalleMovimientoUnidadManejo dm = super.agregarDetalle();
/*  85: 92 */     actualizarOperacion(dm);
/*  86: 93 */     return dm;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void actualizarTransportista()
/*  90:    */   {
/*  91: 97 */     Transportista transportista = this.servicioTransportista.actualizarTransportista(AppUtil.getOrganizacion().getId(), AppUtil.getUsuarioEnSesion()
/*  92: 98 */       .getNombreUsuario());
/*  93: 99 */     this.movimientoUnidadManejo.setTransportista(transportista);
/*  94:100 */     setClasificacion(AjusteUnidadManejoBean.enumClasificacion.Transportista);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void cargarSaldos(ActionEvent event)
/*  98:    */     throws AS2Exception, ExcepcionAS2
/*  99:    */   {
/* 100:105 */     List<SaldoUnidadManejo> listaSaldos = new ArrayList();
/* 101:106 */     List<SaldoUnidadManejo> listaSaldosCliente = new ArrayList();
/* 102:107 */     HashMap<String, DetalleMovimientoUnidadManejo> hmSaldoUnidadManejo = new HashMap();
/* 103:108 */     for (DetalleMovimientoUnidadManejo dm : getMovimientoUnidadManejo().getDetalleMovimientoUnidadManejo())
/* 104:    */     {
/* 105:109 */       dm.setEliminado(true);
/* 106:110 */       dm.setCantidad(0);
/* 107:    */       
/* 108:112 */       dm.setCantidadCliente(0);
/* 109:113 */       if (dm.getUnidadManejo() != null) {
/* 110:114 */         hmSaldoUnidadManejo.put(dm.getUnidadManejo().getCodigo(), dm);
/* 111:    */       }
/* 112:    */     }
/* 113:118 */     if (getMovimientoUnidadManejo().getSucursal() != null)
/* 114:    */     {
/* 115:119 */       if (getMovimientoUnidadManejo().getDocumento().getOperacion() == 1)
/* 116:    */       {
/* 117:120 */         if (getMovimientoUnidadManejo().getTransportista() != null) {
/* 118:121 */           listaSaldos = this.servicioMovimientoUnidadManejo.obtenerSaldoUnidadManejo(AppUtil.getOrganizacion().getId(), 
/* 119:122 */             getMovimientoUnidadManejo().getTransportista(), null);
/* 120:    */         }
/* 121:    */       }
/* 122:    */       else {
/* 123:125 */         listaSaldos = this.servicioMovimientoUnidadManejo.obtenerSaldoUnidadManejo(AppUtil.getOrganizacion().getId(), getMovimientoUnidadManejo()
/* 124:126 */           .getSucursal(), null);
/* 125:    */       }
/* 126:    */     }
/* 127:    */     else
/* 128:    */     {
/* 129:130 */       listaSaldosCliente = this.servicioMovimientoUnidadManejo.obtenerSaldoUnidadManejo(AppUtil.getOrganizacion().getId(), 
/* 130:131 */         getMovimientoUnidadManejo().getEmpresa(), getMovimientoUnidadManejo().getSubempresa(), null);
/* 131:132 */       listaSaldos = this.servicioMovimientoUnidadManejo.obtenerSaldoUnidadManejo(AppUtil.getOrganizacion().getId(), getMovimientoUnidadManejo()
/* 132:133 */         .getTransportista(), null);
/* 133:    */     }
/* 134:135 */     for (SaldoUnidadManejo sum : listaSaldos) {
/* 135:136 */       if (sum.getCantidad() > 0) {
/* 136:137 */         if (hmSaldoUnidadManejo.get(sum.getUnidadManejo().getCodigo()) != null)
/* 137:    */         {
/* 138:138 */           ((DetalleMovimientoUnidadManejo)hmSaldoUnidadManejo.get(sum.getUnidadManejo().getCodigo())).setEliminado(false);
/* 139:139 */           ((DetalleMovimientoUnidadManejo)hmSaldoUnidadManejo.get(sum.getUnidadManejo().getCodigo())).setCantidad(sum.getCantidad());
/* 140:    */         }
/* 141:    */         else
/* 142:    */         {
/* 143:142 */           DetalleMovimientoUnidadManejo dmumt = new DetalleMovimientoUnidadManejo();
/* 144:143 */           dmumt.setIdOrganizacion(sum.getIdOrganizacion());
/* 145:144 */           dmumt.setUnidadManejo(sum.getUnidadManejo());
/* 146:145 */           dmumt.setCantidad(sum.getCantidad());
/* 147:146 */           actualizarOperacion(dmumt);
/* 148:147 */           dmumt.setMovimientoUnidadManejo(getMovimientoUnidadManejo());
/* 149:148 */           getMovimientoUnidadManejo().getDetalleMovimientoUnidadManejo().add(dmumt);
/* 150:149 */           hmSaldoUnidadManejo.put(sum.getUnidadManejo().getCodigo(), dmumt);
/* 151:    */         }
/* 152:    */       }
/* 153:    */     }
/* 154:153 */     if ((isIndicadorTransportista()) && 
/* 155:154 */       (getMovimientoUnidadManejo().getEmpresa() != null)) {
/* 156:155 */       for (SaldoUnidadManejo sum : listaSaldosCliente) {
/* 157:156 */         if (sum.getCantidad() > 0) {
/* 158:157 */           if (hmSaldoUnidadManejo.get(sum.getUnidadManejo().getCodigo()) != null)
/* 159:    */           {
/* 160:158 */             ((DetalleMovimientoUnidadManejo)hmSaldoUnidadManejo.get(sum.getUnidadManejo().getCodigo())).setEliminado(false);
/* 161:159 */             ((DetalleMovimientoUnidadManejo)hmSaldoUnidadManejo.get(sum.getUnidadManejo().getCodigo())).setCantidadCliente(sum.getCantidad());
/* 162:    */           }
/* 163:    */           else
/* 164:    */           {
/* 165:161 */             DetalleMovimientoUnidadManejo dmumt = new DetalleMovimientoUnidadManejo();
/* 166:    */             
/* 167:163 */             dmumt.setIdOrganizacion(sum.getIdOrganizacion());
/* 168:164 */             dmumt.setUnidadManejo(sum.getUnidadManejo());
/* 169:165 */             dmumt.setCantidad(0);
/* 170:166 */             dmumt.setCantidadCliente(sum.getCantidad());
/* 171:167 */             dmumt.setMovimientoUnidadManejo(getMovimientoUnidadManejo());
/* 172:168 */             actualizarOperacion(dmumt);
/* 173:169 */             getMovimientoUnidadManejo().getDetalleMovimientoUnidadManejo().add(dmumt);
/* 174:170 */             hmSaldoUnidadManejo.put(sum.getUnidadManejo().getCodigo(), dmumt);
/* 175:    */           }
/* 176:    */         }
/* 177:    */       }
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void actualizarOperacion(DetalleMovimientoUnidadManejo dm)
/* 182:    */   {
/* 183:179 */     dm.setOperacion(-1);
/* 184:180 */     if (isIndicadorSucursal()) {
/* 185:182 */       if (getMovimientoUnidadManejo().getDocumento().getOperacion() == 1)
/* 186:    */       {
/* 187:183 */         dm.setTransportista(getMovimientoUnidadManejo().getTransportista());
/* 188:184 */         dm.setSucursal(null);
/* 189:    */       }
/* 190:    */       else
/* 191:    */       {
/* 192:186 */         dm.setSucursal(getMovimientoUnidadManejo().getSucursal());
/* 193:187 */         dm.setTransportista(null);
/* 194:    */       }
/* 195:    */     }
/* 196:190 */     if (isIndicadorTransportista()) {
/* 197:191 */       dm.setTransportista(getMovimientoUnidadManejo().getTransportista());
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   public List<UnidadManejo> autocompletarUnidadManejo(String consulta)
/* 202:    */   {
/* 203:196 */     List<UnidadManejo> listUnidadManejo = new ArrayList();
/* 204:197 */     if (isIndicadorTransportista())
/* 205:    */     {
/* 206:198 */       listUnidadManejo = this.servicioMovimientoUnidadManejo.obtenerUnidadManejoPorUsuario(AppUtil.getOrganizacion().getId(), consulta, 
/* 207:199 */         getMovimientoUnidadManejo().getTransportista());
/* 208:200 */       if (getMovimientoUnidadManejo().getEmpresa() != null) {
/* 209:202 */         listUnidadManejo.addAll(this.servicioMovimientoUnidadManejo.obtenerUnidadManejoPorUsuario(AppUtil.getOrganizacion().getId(), consulta, 
/* 210:203 */           getMovimientoUnidadManejo().getEmpresa(), getMovimientoUnidadManejo().getSubempresa()));
/* 211:    */       }
/* 212:    */     }
/* 213:206 */     else if (getMovimientoUnidadManejo().getDocumento().getOperacion() == -1)
/* 214:    */     {
/* 215:207 */       listUnidadManejo = this.servicioMovimientoUnidadManejo.obtenerUnidadManejoPorUsuario(AppUtil.getOrganizacion().getId(), consulta, 
/* 216:208 */         getMovimientoUnidadManejo().getSucursal());
/* 217:    */     }
/* 218:210 */     else if (getMovimientoUnidadManejo().getTransportista() != null)
/* 219:    */     {
/* 220:211 */       listUnidadManejo = this.servicioMovimientoUnidadManejo.obtenerUnidadManejoPorUsuario(AppUtil.getOrganizacion().getId(), consulta, 
/* 221:212 */         getMovimientoUnidadManejo().getTransportista());
/* 222:    */     }
/* 223:216 */     return listUnidadManejo;
/* 224:    */   }
/* 225:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.TransferenciaUnidadManejoBean
 * JD-Core Version:    0.7.0.1
 */