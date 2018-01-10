/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Documento;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Temporal;
/*  22:    */ import javax.persistence.TemporalType;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import javax.validation.constraints.Size;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="consumo_agil_mantenimiento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion"})})
/*  28:    */ public class ConsumoAgilMantenimiento
/*  29:    */   extends EntidadBase
/*  30:    */   implements Serializable
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="consumo_agil_mantenimiento", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="consumo_agil_mantenimiento")
/*  36:    */   @Column(name="id_consumo_agil_mantenimiento")
/*  37:    */   private int idConsumoAgilMantenimiento;
/*  38:    */   @Column(name="id_organizacion")
/*  39:    */   private int idOrganizacion;
/*  40:    */   @Column(name="id_sucursal")
/*  41:    */   private int idSucursal;
/*  42:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  43:    */   @JoinColumn(name="id_documento", nullable=false)
/*  44:    */   @NotNull
/*  45:    */   private Documento documento;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_documento_orden", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   private Documento documentoOrden;
/*  50:    */   @Column(name="numero", nullable=false, length=20)
/*  51:    */   @NotNull
/*  52:    */   @Size(max=20)
/*  53:    */   private String numero;
/*  54:    */   @Column(name="descripcion", length=200, nullable=true)
/*  55:    */   @Size(max=200)
/*  56:    */   private String descripcion;
/*  57:    */   @Temporal(TemporalType.DATE)
/*  58:    */   @Column(name="fecha", nullable=false)
/*  59:    */   @NotNull
/*  60: 75 */   private Date fecha = new Date();
/*  61:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  62:    */   @JoinColumn(name="id_responsable_salida_mercaderia", nullable=true)
/*  63:    */   private PersonaResponsable responsableSalidaMercaderia;
/*  64:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  65:    */   @JoinColumn(name="id_orden_trabajo_mantenimiento", nullable=true)
/*  66:    */   private PersonaResponsable ordenTrabajoMantenimiento;
/*  67:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="consumoAgilMantenimiento")
/*  68: 88 */   private List<DetalleConsumoAgilMantenimiento> listaDetalleConsumoAgilMantenimiento = new ArrayList();
/*  69:    */   
/*  70:    */   public int getId()
/*  71:    */   {
/*  72: 93 */     return this.idConsumoAgilMantenimiento;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdConsumoAgilMantenimiento()
/*  76:    */   {
/*  77: 97 */     return this.idConsumoAgilMantenimiento;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdConsumoAgilMantenimiento(int idConsumoAgilMantenimiento)
/*  81:    */   {
/*  82:101 */     this.idConsumoAgilMantenimiento = idConsumoAgilMantenimiento;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdOrganizacion()
/*  86:    */   {
/*  87:105 */     return this.idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdOrganizacion(int idOrganizacion)
/*  91:    */   {
/*  92:109 */     this.idOrganizacion = idOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getIdSucursal()
/*  96:    */   {
/*  97:113 */     return this.idSucursal;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setIdSucursal(int idSucursal)
/* 101:    */   {
/* 102:117 */     this.idSucursal = idSucursal;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Documento getDocumento()
/* 106:    */   {
/* 107:121 */     return this.documento;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setDocumento(Documento documento)
/* 111:    */   {
/* 112:125 */     this.documento = documento;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Documento getDocumentoOrden()
/* 116:    */   {
/* 117:129 */     return this.documentoOrden;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setDocumentoOrden(Documento documentoOrden)
/* 121:    */   {
/* 122:133 */     this.documentoOrden = documentoOrden;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getNumero()
/* 126:    */   {
/* 127:137 */     return this.numero;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setNumero(String numero)
/* 131:    */   {
/* 132:141 */     this.numero = numero;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getDescripcion()
/* 136:    */   {
/* 137:145 */     return this.descripcion;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setDescripcion(String descripcion)
/* 141:    */   {
/* 142:149 */     this.descripcion = descripcion;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Date getFecha()
/* 146:    */   {
/* 147:153 */     return this.fecha;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setFecha(Date fecha)
/* 151:    */   {
/* 152:157 */     this.fecha = fecha;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public PersonaResponsable getResponsableSalidaMercaderia()
/* 156:    */   {
/* 157:161 */     return this.responsableSalidaMercaderia;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setResponsableSalidaMercaderia(PersonaResponsable responsableSalidaMercaderia)
/* 161:    */   {
/* 162:165 */     this.responsableSalidaMercaderia = responsableSalidaMercaderia;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public PersonaResponsable getOrdenTrabajoMantenimiento()
/* 166:    */   {
/* 167:169 */     return this.ordenTrabajoMantenimiento;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setOrdenTrabajoMantenimiento(PersonaResponsable ordenTrabajoMantenimiento)
/* 171:    */   {
/* 172:173 */     this.ordenTrabajoMantenimiento = ordenTrabajoMantenimiento;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List<DetalleConsumoAgilMantenimiento> getListaDetalleConsumoAgilMantenimiento()
/* 176:    */   {
/* 177:177 */     return this.listaDetalleConsumoAgilMantenimiento;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setListaDetalleConsumoAgilMantenimiento(List<DetalleConsumoAgilMantenimiento> listaDetalleConsumoAgilMantenimiento)
/* 181:    */   {
/* 182:181 */     this.listaDetalleConsumoAgilMantenimiento = listaDetalleConsumoAgilMantenimiento;
/* 183:    */   }
/* 184:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.ConsumoAgilMantenimiento
 * JD-Core Version:    0.7.0.1
 */