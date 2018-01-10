/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.NotNull;
/*  11:    */ import javax.validation.constraints.Size;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="carga_previa_transportista", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  15:    */ public class CargaPreviaTransportista
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="carga_previa_transportista", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="carga_previa_transportista")
/*  22:    */   @Column(name="id_carga_previa_transportista")
/*  23:    */   private int idCargaPreviaTransportista;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="id_sucursal", nullable=false)
/*  27:    */   private int idSucursal;
/*  28:    */   @Column(name="codigo", nullable=false, length=20)
/*  29:    */   @NotNull
/*  30:    */   @Size(max=20)
/*  31:    */   private String codigo;
/*  32:    */   @Column(name="nombre", nullable=false)
/*  33:    */   @Size(max=200)
/*  34:    */   private String nombre;
/*  35:    */   @Column(name="descripcion", nullable=true)
/*  36:    */   @Size(max=200)
/*  37:    */   private String descripcion;
/*  38:    */   @Column(name="activo", nullable=false)
/*  39:    */   private boolean activo;
/*  40:    */   @Column(name="predeterminado", nullable=false)
/*  41:    */   private boolean predeterminado;
/*  42:    */   
/*  43:    */   public int getId()
/*  44:    */   {
/*  45: 62 */     return this.idCargaPreviaTransportista;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getIdCargaPreviaTransportista()
/*  49:    */   {
/*  50: 69 */     return this.idCargaPreviaTransportista;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setIdCargaPreviaTransportista(int idCargaPreviaTransportista)
/*  54:    */   {
/*  55: 73 */     this.idCargaPreviaTransportista = idCargaPreviaTransportista;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdOrganizacion()
/*  59:    */   {
/*  60: 77 */     return this.idOrganizacion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdOrganizacion(int idOrganizacion)
/*  64:    */   {
/*  65: 81 */     this.idOrganizacion = idOrganizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdSucursal()
/*  69:    */   {
/*  70: 85 */     return this.idSucursal;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdSucursal(int idSucursal)
/*  74:    */   {
/*  75: 89 */     this.idSucursal = idSucursal;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getDescripcion()
/*  79:    */   {
/*  80: 93 */     return this.descripcion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setDescripcion(String descripcion)
/*  84:    */   {
/*  85: 97 */     this.descripcion = descripcion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isActivo()
/*  89:    */   {
/*  90:101 */     return this.activo;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setActivo(boolean activo)
/*  94:    */   {
/*  95:105 */     this.activo = activo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean isPredeterminado()
/*  99:    */   {
/* 100:109 */     return this.predeterminado;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setPredeterminado(boolean predeterminado)
/* 104:    */   {
/* 105:113 */     this.predeterminado = predeterminado;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getCodigo()
/* 109:    */   {
/* 110:117 */     return this.codigo;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setCodigo(String codigo)
/* 114:    */   {
/* 115:121 */     this.codigo = codigo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getNombre()
/* 119:    */   {
/* 120:125 */     return this.nombre;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setNombre(String nombre)
/* 124:    */   {
/* 125:129 */     this.nombre = nombre;
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CargaPreviaTransportista
 * JD-Core Version:    0.7.0.1
 */