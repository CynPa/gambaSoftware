/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="caja", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_sucursal", "codigo"})})
/*  18:    */ public class Caja
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="caja", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="caja")
/*  25:    */   @Column(name="id_caja")
/*  26:    */   private int idCaja;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", nullable=false)
/*  32:    */   @NotNull
/*  33:    */   @Size(min=2, max=10)
/*  34:    */   private String codigo;
/*  35:    */   @Column(name="nombre", length=50, nullable=false)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=2, max=50)
/*  38:    */   private String nombre;
/*  39:    */   @Column(name="descripcion", nullable=true)
/*  40:    */   @Size(max=200)
/*  41:    */   private String descripcion;
/*  42:    */   @Column(name="activo", nullable=false)
/*  43:    */   private boolean activo;
/*  44:    */   @Column(name="predeterminado", nullable=false)
/*  45:    */   private boolean predeterminado;
/*  46:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  47:    */   @JoinColumn(name="id_punto_de_venta", nullable=true)
/*  48:    */   private PuntoDeVenta puntoDeVenta;
/*  49:    */   
/*  50:    */   public int getIdCaja()
/*  51:    */   {
/*  52:102 */     return this.idCaja;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIdCaja(int idCaja)
/*  56:    */   {
/*  57:112 */     this.idCaja = idCaja;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdOrganizacion()
/*  61:    */   {
/*  62:121 */     return this.idOrganizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdOrganizacion(int idOrganizacion)
/*  66:    */   {
/*  67:131 */     this.idOrganizacion = idOrganizacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdSucursal()
/*  71:    */   {
/*  72:140 */     return this.idSucursal;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdSucursal(int idSucursal)
/*  76:    */   {
/*  77:150 */     this.idSucursal = idSucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getCodigo()
/*  81:    */   {
/*  82:159 */     return this.codigo;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setCodigo(String codigo)
/*  86:    */   {
/*  87:169 */     this.codigo = codigo;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getDescripcion()
/*  91:    */   {
/*  92:178 */     return this.descripcion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setDescripcion(String descripcion)
/*  96:    */   {
/*  97:188 */     this.descripcion = descripcion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean isActivo()
/* 101:    */   {
/* 102:197 */     return this.activo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setActivo(boolean activo)
/* 106:    */   {
/* 107:207 */     this.activo = activo;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean isPredeterminado()
/* 111:    */   {
/* 112:216 */     return this.predeterminado;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setPredeterminado(boolean predeterminado)
/* 116:    */   {
/* 117:226 */     this.predeterminado = predeterminado;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public PuntoDeVenta getPuntoDeVenta()
/* 121:    */   {
/* 122:235 */     return this.puntoDeVenta;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta)
/* 126:    */   {
/* 127:245 */     this.puntoDeVenta = puntoDeVenta;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getId()
/* 131:    */   {
/* 132:255 */     return this.idCaja;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getNombre()
/* 136:    */   {
/* 137:264 */     return this.nombre;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setNombre(String nombre)
/* 141:    */   {
/* 142:274 */     this.nombre = nombre;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String toString()
/* 146:    */   {
/* 147:284 */     return this.nombre;
/* 148:    */   }
/* 149:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Caja
 * JD-Core Version:    0.7.0.1
 */