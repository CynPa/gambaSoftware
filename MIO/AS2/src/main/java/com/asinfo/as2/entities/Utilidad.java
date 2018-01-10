/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.math.BigDecimal;
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
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.OneToOne;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Temporal;
/*  22:    */ import javax.persistence.TemporalType;
/*  23:    */ import javax.validation.constraints.Min;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="utilidad", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "anio"})})
/*  28:    */ public class Utilidad
/*  29:    */   extends EntidadBase
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="utilidad", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="utilidad")
/*  35:    */   @Column(name="id_utilidad")
/*  36:    */   private int idUtilidad;
/*  37:    */   @Column(name="id_organizacion", nullable=false)
/*  38:    */   private int idOrganizacion;
/*  39:    */   @Column(name="id_sucursal", nullable=false)
/*  40:    */   private int idSucursal;
/*  41:    */   @Column(name="anio", nullable=false)
/*  42:    */   @Min(0L)
/*  43:    */   private int anio;
/*  44:    */   @NotNull
/*  45:    */   @Column(name="valor", nullable=false)
/*  46: 71 */   private BigDecimal valor = BigDecimal.ZERO;
/*  47:    */   @Temporal(TemporalType.DATE)
/*  48:    */   @Column(name="fecha_pago_utilidad", nullable=true)
/*  49:    */   private Date fechaPagoUtilidad;
/*  50:    */   @Enumerated(EnumType.STRING)
/*  51:    */   @Column(name="estado", length=50, nullable=true)
/*  52:    */   private Estado estado;
/*  53:    */   @OneToOne(fetch=FetchType.LAZY)
/*  54:    */   @JoinColumn(name="id_pago_rol", nullable=true)
/*  55:    */   private PagoRol pagoRol;
/*  56:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="utilidad")
/*  57: 90 */   private List<DetalleUtilidad> listaDetalleUtilidad = new ArrayList();
/*  58:    */   
/*  59:    */   public int getIdUtilidad()
/*  60:    */   {
/*  61:111 */     return this.idUtilidad;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdUtilidad(int idUtilidad)
/*  65:    */   {
/*  66:121 */     this.idUtilidad = idUtilidad;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getIdOrganizacion()
/*  70:    */   {
/*  71:130 */     return this.idOrganizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdOrganizacion(int idOrganizacion)
/*  75:    */   {
/*  76:140 */     this.idOrganizacion = idOrganizacion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getIdSucursal()
/*  80:    */   {
/*  81:149 */     return this.idSucursal;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setIdSucursal(int idSucursal)
/*  85:    */   {
/*  86:159 */     this.idSucursal = idSucursal;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getAnio()
/*  90:    */   {
/*  91:168 */     return this.anio;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setAnio(int anio)
/*  95:    */   {
/*  96:178 */     this.anio = anio;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public BigDecimal getValor()
/* 100:    */   {
/* 101:187 */     return this.valor;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setValor(BigDecimal valor)
/* 105:    */   {
/* 106:197 */     this.valor = valor;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public PagoRol getPagoRol()
/* 110:    */   {
/* 111:206 */     return this.pagoRol;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setPagoRol(PagoRol pagoRol)
/* 115:    */   {
/* 116:216 */     this.pagoRol = pagoRol;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List<DetalleUtilidad> getListaDetalleUtilidad()
/* 120:    */   {
/* 121:225 */     return this.listaDetalleUtilidad;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setListaDetalleUtilidad(List<DetalleUtilidad> listaDetalleUtilidad)
/* 125:    */   {
/* 126:235 */     this.listaDetalleUtilidad = listaDetalleUtilidad;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int getId()
/* 130:    */   {
/* 131:245 */     return this.idUtilidad;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Date getFechaPagoUtilidad()
/* 135:    */   {
/* 136:252 */     return this.fechaPagoUtilidad;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setFechaPagoUtilidad(Date fechaPagoUtilidad)
/* 140:    */   {
/* 141:260 */     this.fechaPagoUtilidad = fechaPagoUtilidad;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Estado getEstado()
/* 145:    */   {
/* 146:267 */     return this.estado;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setEstado(Estado estado)
/* 150:    */   {
/* 151:275 */     this.estado = estado;
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Utilidad
 * JD-Core Version:    0.7.0.1
 */