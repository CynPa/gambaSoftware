/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Temporal;
/*  21:    */ import javax.persistence.TemporalType;
/*  22:    */ import javax.validation.constraints.Digits;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import javax.validation.constraints.Size;
/*  25:    */ import org.hibernate.annotations.ColumnDefault;
/*  26:    */ 
/*  27:    */ @Entity
/*  28:    */ @Table(name="cierre_caja")
/*  29:    */ public class CierreCaja
/*  30:    */   extends EntidadBase
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="cierre_caja", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="cierre_caja")
/*  36:    */   @Column(name="id_cierre_caja")
/*  37:    */   private int idCierreCaja;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   private int idOrganizacion;
/*  40:    */   @Column(name="id_sucursal", nullable=false)
/*  41:    */   private int idSucursal;
/*  42:    */   @Column(name="valor", precision=12, scale=2, nullable=false)
/*  43:    */   @Digits(integer=12, fraction=2)
/*  44: 69 */   private BigDecimal valor = BigDecimal.ZERO;
/*  45:    */   @Column(name="total_usuario", precision=12, scale=2, nullable=false)
/*  46:    */   @ColumnDefault("0")
/*  47:    */   @NotNull
/*  48: 73 */   private BigDecimal totalUsuario = BigDecimal.ZERO;
/*  49:    */   @Temporal(TemporalType.DATE)
/*  50:    */   @Column(name="fecha_hasta", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private Date fechaHasta;
/*  53:    */   @Column(name="estado", nullable=false)
/*  54:    */   private Estado estado;
/*  55:    */   @Column(name="numero", nullable=false, length=20, insertable=true, updatable=false)
/*  56:    */   @NotNull
/*  57:    */   @Size(max=20)
/*  58:    */   private String numero;
/*  59:    */   @Column(name="id_dispositivo_sincronizacion", nullable=true)
/*  60:    */   private Integer idDispositivoSincronizacion;
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_caja", nullable=true)
/*  63:    */   private Caja caja;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_usuario", nullable=false)
/*  66:    */   private EntidadUsuario usuario;
/*  67:    */   @OneToMany(mappedBy="cierreCaja")
/*  68:    */   List<DetalleCierreCaja> listaDetalleCierreCaja;
/*  69:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="cierreCaja")
/*  70:108 */   private List<DetalleDenominacionFormaCobro> listDetalleDenominacionFormaCobro = new ArrayList();
/*  71:    */   
/*  72:    */   public int getId()
/*  73:    */   {
/*  74:136 */     return this.idCierreCaja;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdCierreCaja()
/*  78:    */   {
/*  79:145 */     return this.idCierreCaja;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdCierreCaja(int idCierreCaja)
/*  83:    */   {
/*  84:155 */     this.idCierreCaja = idCierreCaja;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getIdOrganizacion()
/*  88:    */   {
/*  89:164 */     return this.idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdOrganizacion(int idOrganizacion)
/*  93:    */   {
/*  94:174 */     this.idOrganizacion = idOrganizacion;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getIdSucursal()
/*  98:    */   {
/*  99:183 */     return this.idSucursal;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setIdSucursal(int idSucursal)
/* 103:    */   {
/* 104:193 */     this.idSucursal = idSucursal;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public BigDecimal getValor()
/* 108:    */   {
/* 109:202 */     return this.valor;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setValor(BigDecimal valor)
/* 113:    */   {
/* 114:212 */     this.valor = valor;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Date getFechaHasta()
/* 118:    */   {
/* 119:221 */     return this.fechaHasta;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setFechaHasta(Date fechaHasta)
/* 123:    */   {
/* 124:231 */     this.fechaHasta = fechaHasta;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public EntidadUsuario getUsuario()
/* 128:    */   {
/* 129:240 */     return this.usuario;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setUsuario(EntidadUsuario usuario)
/* 133:    */   {
/* 134:250 */     this.usuario = usuario;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public List<DetalleCierreCaja> getListaDetalleCierreCaja()
/* 138:    */   {
/* 139:259 */     return this.listaDetalleCierreCaja;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setListaDetalleCierreCaja(List<DetalleCierreCaja> listaDetalleCierreCaja)
/* 143:    */   {
/* 144:269 */     this.listaDetalleCierreCaja = listaDetalleCierreCaja;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Estado getEstado()
/* 148:    */   {
/* 149:278 */     return this.estado;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setEstado(Estado estado)
/* 153:    */   {
/* 154:288 */     this.estado = estado;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Caja getCaja()
/* 158:    */   {
/* 159:297 */     return this.caja;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setCaja(Caja caja)
/* 163:    */   {
/* 164:307 */     this.caja = caja;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String getNumero()
/* 168:    */   {
/* 169:316 */     return this.numero;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setNumero(String numero)
/* 173:    */   {
/* 174:326 */     this.numero = numero;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Integer getIdDispositivoSincronizacion()
/* 178:    */   {
/* 179:330 */     return this.idDispositivoSincronizacion;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setIdDispositivoSincronizacion(Integer idDispositivoSincronizacion)
/* 183:    */   {
/* 184:334 */     this.idDispositivoSincronizacion = idDispositivoSincronizacion;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<DetalleDenominacionFormaCobro> getListDetalleDenominacionFormaCobro()
/* 188:    */   {
/* 189:338 */     return this.listDetalleDenominacionFormaCobro;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setListDetalleDenominacionFormaCobro(List<DetalleDenominacionFormaCobro> listDetalleDenominacionFormaCobro)
/* 193:    */   {
/* 194:342 */     this.listDetalleDenominacionFormaCobro = listDetalleDenominacionFormaCobro;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public BigDecimal getTotalUsuario()
/* 198:    */   {
/* 199:346 */     return this.totalUsuario;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setTotalUsuario(BigDecimal totalUsuario)
/* 203:    */   {
/* 204:350 */     this.totalUsuario = totalUsuario;
/* 205:    */   }
/* 206:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CierreCaja
 * JD-Core Version:    0.7.0.1
 */