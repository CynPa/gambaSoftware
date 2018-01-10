/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.EnumType;
/*  11:    */ import javax.persistence.Enumerated;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.OneToOne;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.persistence.Temporal;
/*  23:    */ import javax.persistence.TemporalType;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ import javax.validation.constraints.Size;
/*  26:    */ 
/*  27:    */ @Entity
/*  28:    */ @Table(name="recepcion_devolucion_transportista", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_recepcion_devolucion_transportista"})})
/*  29:    */ public class RecepcionDevolucionTransportista
/*  30:    */   extends EntidadBase
/*  31:    */   implements Serializable
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @Id
/*  35:    */   @TableGenerator(name="recepcion_devolucion_transportista", initialValue=0, allocationSize=50)
/*  36:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="recepcion_devolucion_transportista")
/*  37:    */   @Column(name="id_recepcion_devolucion_transportista")
/*  38:    */   private int idRecepcionDevolucionTransportista;
/*  39:    */   @Column(name="id_organizacion", nullable=false)
/*  40:    */   @NotNull
/*  41:    */   private int idOrganizacion;
/*  42:    */   @Temporal(TemporalType.DATE)
/*  43:    */   @Column(name="fecha", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private Date fecha;
/*  46:    */   @Temporal(TemporalType.DATE)
/*  47:    */   @Column(name="fecha_procesamiento", nullable=true)
/*  48:    */   private Date fechaProcesamiento;
/*  49:    */   @Column(name="estado", nullable=false)
/*  50:    */   @Enumerated(EnumType.ORDINAL)
/*  51:    */   private Estado estado;
/*  52:    */   @Column(name="nota", nullable=true, length=500)
/*  53:    */   @Size(max=500)
/*  54:    */   private String nota;
/*  55:    */   @Column(name="producto_bueno", nullable=false)
/*  56:    */   private boolean productoBueno;
/*  57:    */   @OneToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_transportista", nullable=true)
/*  59:    */   private Transportista transportista;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  62:    */   private Sucursal sucursal;
/*  63:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  64:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  65:    */   private Empresa empresa;
/*  66:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_subempresa", nullable=true)
/*  68:    */   private Subempresa subempresa;
/*  69:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="recepcionDevolucionTransportista")
/*  70:101 */   private List<PreDevolucionCliente> listaPreDevolucionCliente = new ArrayList();
/*  71:    */   
/*  72:    */   public int getId()
/*  73:    */   {
/*  74:108 */     return this.idRecepcionDevolucionTransportista;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdRecepcionDevolucionTransportista()
/*  78:    */   {
/*  79:112 */     return this.idRecepcionDevolucionTransportista;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdRecepcionDevolucionTransportista(int idRecepcionDevolucionTransportista)
/*  83:    */   {
/*  84:116 */     this.idRecepcionDevolucionTransportista = idRecepcionDevolucionTransportista;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getIdOrganizacion()
/*  88:    */   {
/*  89:120 */     return this.idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdOrganizacion(int idOrganizacion)
/*  93:    */   {
/*  94:124 */     this.idOrganizacion = idOrganizacion;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Date getFecha()
/*  98:    */   {
/*  99:128 */     return this.fecha;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setFecha(Date fecha)
/* 103:    */   {
/* 104:132 */     this.fecha = fecha;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Estado getEstado()
/* 108:    */   {
/* 109:136 */     return this.estado;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setEstado(Estado estado)
/* 113:    */   {
/* 114:140 */     this.estado = estado;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String getNota()
/* 118:    */   {
/* 119:144 */     return this.nota;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setNota(String nota)
/* 123:    */   {
/* 124:148 */     this.nota = nota;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Transportista getTransportista()
/* 128:    */   {
/* 129:152 */     return this.transportista;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setTransportista(Transportista transportista)
/* 133:    */   {
/* 134:156 */     this.transportista = transportista;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public List<PreDevolucionCliente> getListaPreDevolucionCliente()
/* 138:    */   {
/* 139:160 */     return this.listaPreDevolucionCliente;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setListaPreDevolucionCliente(List<PreDevolucionCliente> listaPreDevolucionCliente)
/* 143:    */   {
/* 144:164 */     this.listaPreDevolucionCliente = listaPreDevolucionCliente;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Sucursal getSucursal()
/* 148:    */   {
/* 149:168 */     return this.sucursal;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setSucursal(Sucursal sucursal)
/* 153:    */   {
/* 154:172 */     this.sucursal = sucursal;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean isProductoBueno()
/* 158:    */   {
/* 159:176 */     return this.productoBueno;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setProductoBueno(boolean productoBueno)
/* 163:    */   {
/* 164:180 */     this.productoBueno = productoBueno;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Date getFechaProcesamiento()
/* 168:    */   {
/* 169:184 */     return this.fechaProcesamiento;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setFechaProcesamiento(Date fechaProcesamiento)
/* 173:    */   {
/* 174:188 */     this.fechaProcesamiento = fechaProcesamiento;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Empresa getEmpresa()
/* 178:    */   {
/* 179:192 */     return this.empresa;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setEmpresa(Empresa empresa)
/* 183:    */   {
/* 184:196 */     this.empresa = empresa;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public Subempresa getSubempresa()
/* 188:    */   {
/* 189:200 */     return this.subempresa;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setSubempresa(Subempresa subempresa)
/* 193:    */   {
/* 194:204 */     this.subempresa = subempresa;
/* 195:    */   }
/* 196:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RecepcionDevolucionTransportista
 * JD-Core Version:    0.7.0.1
 */